const express = require('express');
const app = express();

const workerpool = require("workerpool");
const pool = workerpool.pool({minWorkers: 10, maxWorkers: 10, maxQueueSize: 1, workerType: "thread"});

const {Worker} = require("worker_threads");

let counter = 0;

const complexTask = () => {
    console.log("Worker will now start the computation.");
    let myCounter = 0;
    while (myCounter < 5000000000) {
        myCounter++;
    }
    console.log("Worker is done with the computation.");
    return myCounter;
}

app.get("/", (_, res) => {
    console.log("This request is super fast, so the single node thread will be able to handle this get concurrently with no problems");
    counter ++;
    res.status(200).json({counter});
});

app.get("/heavy", (_, res) => {
    console.log("This request is super heary and since this task is not one of the ones managed by the worker pool (i.e. fs, crypto, zlib and dns) the app will freeze stuck in this loop, so node won't be able to serve the super fast / endpoint");
    for(let i = 0; i < 10000000000; i ++) {}
    counter ++;
    res.status(200).json({counter});
});

app.get("/heavyWorker1", (_, res) => {
    pool.exec(complexTask).then(_ => {
        console.log("Task done");
        counter ++;
        res.status(200).json({counter});
    }).catch(err => {
        console.log(err);
    });
});

app.get("/heavyWorker2", (_, res) => {
    let worker = new Worker("./myWorker.js");
    worker.on("message", () => {
        console.log("Task done");
        counter ++;
        res.status(200).json({counter});
    });
});

app.listen(9000, () => {
    console.log("App is listening");
})

/*
 * Notes:
 * https://www.npmjs.com/package/workerpool
 * 1 - If the queue gets filled and a new task is sent to the queue, the program will throw an exception and end abruptly.
 * 2 - After using pool.terminate, the worker is killed, so it is not available anymore. For instance, if we create max 10 workers, if after each 
 *     instruction we use ".then(res => {pool.terminate()})" only 10 requests will be executed, any new request to the heavyWorker1 endpoint will
 *     freeze forever.
 */