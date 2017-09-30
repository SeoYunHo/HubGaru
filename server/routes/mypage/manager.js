"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.getUserInfo = (id) => {
    let response = {
        user: null
    };
    let stateCode

    conn.query('select * from garu', garuId, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 0) {
            stateCode=200;
            response.user={

            }
        }
        callback(stateCode,response);
    });
}

manager.getUserHub = (id) => {
    let response = {
        hub: []
    };

    let stateCode;
    conn.query('select * from hub where id=?', id, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 0) {
            stateCode=200;
            let hub = {
                garuId: rows[i].garuid,
                leaderId: rows[i].leaderid,
                name: rows[i].name,
                intro: rows[i].intro,
                img: rows[i].img
            }
            response.hub.push(hub);
        }
        callback(stateCode,response);
    });
}

manager.getUserGaru = (id) => {
    let response = {
        garu: []
    };
    let stateCode;

    conn.query('select * from garu where id=?', id, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 0) {
            stateCode=200;
            let garu = {
                garuId: rows[i].garuid,
                leaderId: rows[i].leaderid,
                name: rows[i].name,
                intro: rows[i].intro,
                img: rows[i].img
            }
            response.garu.push(garu);
        }
        callback(stateCode,response);
    });
}  

module.exports = manager;
