"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.getUserInfo = (id, callback) => {
    let response = {
        user: null
    };
    let statusCode

    conn.query('select * from account where id =? ', id, function (err, rows) {
        console.log(rows);
        if (err) statusCode = 500;
        else if (rows.length == 1) {
            statusCode = 200;
            response.user = {
                part: rows[0].part,
                intro: rows[0].user_intro,
                picture: rows[0].picture_uri,
                name: rows[0].name,
                phone: rows[0].phone
            }
        } else statusCode = 400;
        callback(statusCode, response);
    });
}

manager.getUserHub = (id, callback) => {
    let response = {
        hub: []
    };

    let statusCode;

    getGaruId(id)
        .then((garuId) => {

            for (var i = 0; i < garuId.length; i++) {
                (function (i) {
                    conn.query('select * from hub where garu_id=?', garuId[i].garu_id, function (err, rows) {
                        console.log(i == garuId.length-1);
                        console.log(garuId[i].garu_id);
                        if (err) statusCode = 500;
                        else if (rows.length > 0) {
                            statusCode = 200;
                            let hub = {
                                hubId: rows[0].hub_id,
                                garuId: rows[0].garu_id,
                                name: rows[0].name,
                                img: rows[0].img,
                                file: rows[0].file_url
                            }
                            response.hub.push(hub);
                            if (i == garuId.length-1) {
                                statusCode = 200;
                                callback(statusCode, response);
                            }
                        }
                    });
                })(i)
            }
        })
}

manager.getUserGaru = (id, callback) => {
    let response = {
        garu: []
    };
    let statusCode;

    getGaruId(id)
        .then((garuId) => {
            for (var i = 0; i < garuId.length; i++) {
                (function (i) {
                    conn.query('select * from garu where garu_id=?', garuId[i].garu_id, function (err, rows) {
                        if (err) statusCode = 500;
                        else if (rows.length > 0) {
                            statusCode = 200;
                            let garu = {
                                garuId: rows[0].garu_id,
                                leaderId: rows[0].leader_id,
                                name: rows[0].name,
                                intro: rows[0].intro,
                                img: rows[0].img
                            }
                            response.garu.push(garu);
                            if (i == garuId.length - 1) {
                                statusCode = 200;
                                callback(statusCode, response);
                            }
                        }
                    });
                })(i)
            }
        })
}

function getGaruId(userId) {
    return new Promise((resolve, reject) => {
        conn.query('select * from member where user_id=?', userId, function (err, rows) {
            console.log(rows);
            resolve(rows);
        });
    })
}
module.exports = manager;