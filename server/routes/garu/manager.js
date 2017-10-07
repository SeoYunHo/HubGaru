"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.checkId= (garuId) => {
    conn.query('select * from garu where garu_id',garuId, function(err, rows){
        if(err) return true;
        else if(rows.length==0) return false;
        else return true;
    });
}
manager.addGaru = (garuId, leaderId, intro, name, file, img, callback) => {
    let stateCode;
    conn.query('insert into garu value(?,?,?,?,?,?);', [garuId, leaderId, intro, name, file, img], function (err, result) {
        if (err) stateCode=500;
        else if (!!result.affectedRows) stateCode=201;
        callback(stateCode);
    });
}

manager.getGarues = (callback) => {
    let response = {
        garu: []
    };
    let stateCode;

    conn.query('select * from garu', null, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length >= 0) {
            stateCode = 200;
            for (var i = 0; i < rows.length; i++) {
                let garu = {
                    garuId: rows[i].garu_id,
                    leaderId: rows[i].leader_id,
                    name: rows[i].name,
                    intro: rows[i].intro,
                    img: rows[i].img,
                    file: rows[i].file
                }
                response.garu.push(garu);
            }
        }
        callback(stateCode, response);
    });

}

module.exports = manager;