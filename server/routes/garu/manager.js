"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.addGaru = (garuId, leaderId, intro, name) => {
    let response = {
        success: false
    };

    conn.query('insert into garu value(?,?,?,?);', [garuId, leaderId, intro, name], function (err, result) {
        if (err) response.error = true;
        else if (result.affectedRows) response.success = true;
        callback(response);
    });
}

manager.getGaru = (garuId) => {
    let response = {
        garu: []
    };

    conn.query('select * from garu where garuid = ?', garuId, function (err, rows) {
        if (err) response.error = true;
        else if (rows.length == 0) {
            let garu = {
                garuId: rows[i].garuid,
                leaderId: rows[i].leaderid,
                name: rows[i].name,
                intro: rows[i].intro
            }
            response.garu.push(garu);
        }
        callback(response);
    });

}

module.exports = manager;