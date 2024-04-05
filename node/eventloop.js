const crypto = require('node:crypto');

/*
 * With this example, we can know:
 *      1 - How many threads the libuv module has. To do this, we just need to increment MAX_TASKS until the the time taken for one of the tasks
 *          is almost double the other ones. In this case, the number of task is 5, meaning that the default number of libuv threads is 4.
 *      2 - How we can increase the number of libuv threads by leveraging additional cores. In this case, the number of cores is 6, and by 
 *          default the number of threads of libuv is 4. So if we increment the pool size to 6, we'll see that the 6 tasks take the same time,
 *          which must match the original time of the 4 threads.
 *      3 - If the value of UV_THREADPOOL_SIZE is higher than the number of cores, we'll see a decrement in the performance of all tasks since 
 *          libuv will have to realocate the additional tasks among the diferent cores, meaning each task will not have each core for itself.
 */
process.env.UV_THREADPOOL_SIZE = 6;
const start = Date.now();
const MAX_TASKS = 6;
for(let i = 0; i < MAX_TASKS; i ++) {
    crypto.pbkdf2("password", "salt", 100000, 512, "sha512", () => {
        console.log(`Hash: ${i + 1}`, Date.now() - start);
    });
}