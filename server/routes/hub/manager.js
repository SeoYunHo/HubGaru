"use strict";
let conn = require('../../DBConnection');

let manager = {};

manager.checkId = (hubId) => {
    conn.query('select * from hub where hub_id=?', hubId, function (err, rows) {
        if (err) return true;
        else if (rows.length == 0) return false;
        else return true;
    });
}

manager.addHub = (hubId, garuId, file, name, img, date, callback) => {
    let stateCode;

    conn.query('insert into hub values (?,?,?,?,?,?,0);', [hubId, garuId, file, name, img, date], function (err, result) {
        if (err) stateCode = 500;
        else if (result.affectedRows) stateCode = 201;
        callback(stateCode);
    });
}

manager.getHub = (callback) => {
    let response = {
        hub: []
    }
    let stateCode;

    conn.query('select * from hub;', null, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length >= 0) {
            stateCode = 200;
            for (let i = 0; i < rows.length; i++) {
                let hub = {
                    file: rows[i].file_url,
                    img: rows[i].img,
                    name: rows[i].name,
                    garuId: rows[i].garu_id,
                    hubId: rows[i].hub_id,
                    date: rows[i].date,
                    good: rows[i].good
                }
                response.hub.push(hub);
            }
        }
        callback(stateCode, response);
    });
}

manager.getDetailHub = (callback) => {
    let response = {
        hub: null
    };

    let stateCode


    conn.query('select * from hub where garu_id=?', garuId, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length >= 0) {
            stateCode = 200;
            for (let i = 0; i < rows.length; i++) {
                let hub = {
                    file: rows[i].file_url,
                    img: rows[i].img,
                    name: rows[i].name,
                    garuId: rows[i].garu_id
                }
                response.hub = hub;
            }
        }
        callback(stateCode, response);
    });

}

manager.addGood = (hubId, callback) => {
    let stateCode;

    conn.query('update hub set good=good+1 where hub_id=?', hubId, function (err, result) {
        if (err) stateCode = 500;
        else if (result.affectedRows) stateCode = 201;
        else stateCode = 400;

        callback(stateCode);
    });
}

manager.deleteGood = (hubId, callback) => {
    let stateCode;
    conn.query('update hub set good=good-1 where hub_id=?', hubId, function (err, result) {
        if (err) stateCode = 500;
        else if (result.affectedRows) stateCode = 201;
        else stateCode = 400;

        callback(stateCode);
    });
}

manager.addComment = (hubId, comment, id, date, callback) => {
    let stateCode;
    conn.query('insert into comment values(?,?,?,?)', [hubId, comment, id, date], function (err, result) {
        if (err) stateCode = 500;
        else if (result.affectedRows) stateCode = 201;
        else stateCode = 400;

        callback(stateCode);
    });
}

manager.getComment = (hubId, callback) => {
    let stateCode;
    let response = {
        comment: []
    }
    conn.query('select * from comment where hub_id= ?', hubId, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length >= 0) {
            stateCode = 201;
            for (var i = 0; i < rows.length; i++) {
                let comment = {
                    comment: rows[i].comment,
                    id: rows[i].id,
                    date: rows[i].date
                }
                response.comment.push(comment);
            }
        } else stateCode = 400;

        callback(stateCode, response);
    });
}

manager.getGood = (hubId, callback) => {
    let response = {
        good: null
    };
    let stateCode


    conn.query('select * from hub where hub_id=?', hubId, function (err, rows) {
        if (err) stateCode = 500;
        else if (rows.length == 1) {
            stateCode = 200;
            response.good=rows[0].good;
        }else stateCode=400;

        callback(stateCode, response);
    });

}

module.exports = manager;