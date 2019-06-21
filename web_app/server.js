const express = require('express');
const server = express();

server.set('view engine', 'ejs');

server.get('/', function (req, res) {
    res.render('index');
});

server.listen(3000, function () {
    console.log('Listening on port 3000!')
});