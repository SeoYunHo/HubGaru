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
manager.addGaru = (garuId, leaderId, name, intro, file, img, callback) => {
    let statusCode;
    conn.query('insert into garu value(?,?,?,?,?,?);', [garuId, leaderId, intro, name, file, img], function (err, result) {
        if (err) statusCode=500;
        else if (!!result.affectedRows){
            conn.query('insert into member value(?,?)',[garuId, leaderId], function(err, result){
                if(err) statusCode=500;
                else if(!!result.affectedRows) statusCode=201;
                callback(statusCode);
            });
        } 
    });
}

manager.getGarues = (callback) => {
    let response = {
        garu: []
    };
    let statusCode;

    conn.query('select * from garu', null, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
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
        callback(statusCode, response);
    });
}

manager.getMember = (garuId, callback) => {
    let response = {
        member: []
    };
    let statusCode;

    conn.query('select * from member where garu_id=?', garuId, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
            for (var i = 0; i < rows.length; i++) {
                let member=rows[i].user_id;
                response.member.push(member);
            }
        }
        callback(statusCode, response);
    });
}

manager.addMember = (garuId, userId, callback) => {
    let statusCode;
    conn.query('insert into member values(?,?);', [garuId, userId], function (err, result) {
        if (err) statusCode=500;
        else if (!!result.affectedRows) statusCode=201;
        else statusCode=400;

        callback(statusCode);
    });
}


module.exports = manager;