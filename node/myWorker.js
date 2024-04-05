const {parentPort} = require("worker_threads");

const complexTask = () => {
    console.log("Worker will now start the computation.");
    let myCounter = 0;
    while (myCounter < 5000000000) {
        myCounter++;
    }
    console.log("Worker is done with the computation.");
    return myCounter;
}

complexTask();

parentPort.postMessage("message");