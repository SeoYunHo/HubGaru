"use strict";
let http = require('http');
let express = require('express');
let path = require('path');
let bodyParser = require('body-parser');
let cookieParser = require('cookie-parser');
let errorHandler = require('errorhandler');
let expressSession = require('express-session');
let app = express();
var garu = require('./routes/garu/router');
var user = require('./routes/user/router');
var hub = require('./routes/hub/router');
var port = '8080';

app.set('port', port);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
app.use(cookieParser());


app.use(expressSession({
    secret: 'my key',
    resave: true,
    saveUninitialized: true
}));

app.use('/', user);
app.use('/', garu);
app.use('/', hub);

app.listen(app.get('port'), function () {
    console.log('Example app listening on' + app.get('port') + 'port');
});