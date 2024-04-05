/*
 * __dirname: path to current directory
 * __filename: file name
 * require: function to use modules (CommonJS)
 * module: info about current module (file)
 * process: info about environment where program is being executed
 */

/*
 * The event loop.
 * Node is a single-thread environment, meaning that there is a single thread executing all code. However, this is not exactly true.
 * The event loop is a single thread, initiated by node, and is responsible to manage all event callbacks. This is how node works:
 *  - All sync code is managed by the event loop, so is there's a heavy sync code, the rest of the calls will get blocked.
 *  - The event loop manages the callback function, meaning that when there's an async function, the event loop sends the execution of that function
 *    to a pool of threads on the backend, called worker pool, and when the function is done, the callback is sent back to the event loop, which 
 *    executes the next step of that program. As we can see, node is not based on a single thread, only the event loop.
 *  - The event loop executes the callback functions based on priorities. From highest to lowest:
 *      1 - Timers: setTimeout, setInterval.
 *      2 - Pending callbacks: I/O (disk, network and in general blocking operations) related callbacks that were deferred.
 *      3 - Poll: retrieve new I/O events and execute their callbacks immediately if possible. If not, it'll defer the execution and will register
 *          them as pending callbacks for the next iteration. It'll also check whether there's any timer callbacks and if so, it'll jump to step 1.
 *      4 - Check: execute setImmediate() callbacks.
 *      5 - Close callbacks: execute all 'close' event callbacks.
 *      6 - Close the program is there's no pending events. If not, go to 1.
 * 
 * In general, Node.js is fast when the work associated with each client at any given time is "small". This applies to callbacks on the Event Loop 
 * and tasks on the Worker Pool. The tasks that will be sent to the worker pool are:
 *      - All fs (File system) operations, except fs.FSWatcher()
 *      - Some functions from Crypto lib
 *      - Almost all Zlib functions
 *      - dns.lookup(), dns.lookupService()
 * Exceptional tutorial about the event loop here: https://www.builder.io/blog/visual-guide-to-nodejs-event-loop
 *      - What it is, what is executed within the event loop.
 *      - Only native functions (with access to the event loop) are asynchronous. The other functions are executed by node single thread (i.e. they are 
 *        blocking).
 *      - Async code is only executed after all sync code has been executed.
 *      - To create a function that calls its callback asynchronously, you have to use some platform-provided async primitive (typically IO-related) on 
 *        it - timers, reading from the filesystem, making a request etc.
 * Great video about libuv 8 threads here: https://www.youtube.com/watch?v=3JYNNf3Iljo
 * 
 * This means that even async code (e.g. setTimeout) where a heavy task not related to those 4 ones is executed (e.g. super big loop) will block
 * the event loop as that code will be executed by the event loop and not by the worker pool. To understand how we can create a custom pool,
 * go to thread.js file.
 */
const readline = require('readline');
const fs = require('fs');
const util = require('util');
const readFileWithPromise = util.promisify(fs.readFile)

/*
 * Other util modules:
 *      - os, path
 *      - lodash (great for Iterating arrays, objects, & strings, Manipulating & testing values, Creating composite functions)
 *      - http
 */

console.log(__dirname);
console.log(__filename);
var cont = 0, intervalId = setInterval(() => {
    cont ++;
    console.log(cont);
    if(cont == 5)
        clearInterval(intervalId);
}, 1000);

/*
 * READ AND WRITE COMMAND LINE
 */
const r1 = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

r1.question("Please enter the information for the file: ", (info) => {
    r1.write("Info to write to the file: " + info + "\n");
    fs.writeFileSync("./files/output.txt", info);
    r1.close();
});

r1.addListener('close', () => {
    r1.write("Interface closed ...\n");
    process.exit(0);
});

/*
 * READ AND WRITE FILES
 */
console.log("Reading file synchronously ...");
try {
    let textFromFile = fs.readFileSync('./files/example.txt', 'utf-8');
    console.log(textFromFile);
} catch (err) {
    if (err.code !== 'ENOENT')
        throw err;
    console.error("File not found");
}
try {
    let jsonFromFile = fs.readFileSync('./files/example.json', 'utf-8');
    console.log(jsonFromFile);
} catch (err) {
    if (err.code !== 'ENOENT')
        throw err;
    console.error("File not found");
}

console.log("Reading file asynchronously with Promises to avoid callback hell...");
readFileWithPromise('./files/example.txt', 'utf-8')
    .then(data => console.log(data))
    .catch(err => {
        if (err.code !== 'ENOENT')
            throw err;
        console.error("File not found");
    });

console.log("Reading file asynchronously with Async/Await to avoid callback hell...");
async function readWithAsyncAwait(fileName) {
    try {
        let dataReadWithAwait = await readFileWithPromise(fileName, 'utf-8');
        console.log(dataReadWithAwait);
    } catch(err) {
        if (err.code !== 'ENOENT')
            throw err;
        console.error("File not found");
    }
}
readWithAsyncAwait('./files/example.txt');