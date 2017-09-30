"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.addGaru = (garuId, leaderId, intro, name) => {
    let stateCode;
    conn.query('insert into garu value(?,?,?,?);', [garuId, leaderId, intro, name], function (err, result) {
        if (err) stateCode=500;
        else if (result.affectedRows) stateCode=200;
        callback(stateCode);
    });
}

manager.getGaru = (garuId) => {
    let response = {
        garu: []
    };
    let stateCode;

    conn.query('select * from garu', null, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length == 0) {
            stateCode = 200;
            for (var i = 0; i < rows.length; i++) {
                let garu = {
                    garuId: rows[i].garuid,
                    leaderId: rows[i].leaderid,
                    name: rows[i].name,
                    intro: rows[i].intro,
                    img: rows[i].img
                }
                response.garu.push(garu);
            }
        }
        callback(stateCode, response);
    });

}

module.exports = manager;