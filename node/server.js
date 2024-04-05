var express = require('express');
var app = express();

/*
 * Inject common variables through use, before all others use
 */
app.use((_req, res, next) => {
    res.variable = "myVariable";
    next();
});

app.get('/', function(_req, res, _next) {
    console.log("Requeste received!");
    res.status(200).json({message: "Hello from the server"});
});

app.get('/hello', function(_req, res, _next) {
    console.log("Requeste received!");
    res.status(200).send("<h1>Hello</h1>");
});

app.listen(8090, 'localhost', () => {
    console.log("Server started!");
});