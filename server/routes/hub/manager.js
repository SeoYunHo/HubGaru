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
    let statusCode;

    conn.query('insert into hub values (?,?,?,?,?,?,0);', [hubId, garuId, file, name, img, date], function (err, result) {
        if (err) statusCode = 500;
        else if (result.affectedRows) statusCode = 201;
        callback(statusCode);
    });
}

manager.getHub = (callback) => {
    let response = {
        hub: []
    }
    let statusCode;

    conn.query('select * from hub;', null, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
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
        callback(statusCode, response);
    });
}

manager.getDetailHub = (callback) => {
    let response = {
        hub: null
    };

    let statusCode


    conn.query('select * from hub where garu_id=?', garuId, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
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
        callback(statusCode, response);
    });

}

manager.addGood = (hubId, callback) => {
    let statusCode;

    conn.query('update hub set good=good+1 where hub_id=?', hubId, function (err, result) {
        if (err) statusCode = 500;
        else if (result.affectedRows) statusCode = 201;
        else statusCode = 400;

        callback(statusCode);
    });
}

manager.deleteGood = (hubId, callback) => {
    let statusCode;
    conn.query('update hub set good=good-1 where hub_id=?', hubId, function (err, result) {
        if (err) statusCode = 500;
        else if (result.affectedRows) statusCode = 201;
        else statusCode = 400;

        callback(statusCode);
    });
}

manager.addComment = (hubId, comment, id, date, callback) => {
    let statusCode;
    conn.query('insert into comment values(?,?,?,?)', [hubId, comment, id, date], function (err, result) {
        if (err) statusCode = 500;
        else if (result.affectedRows) statusCode = 201;
        else statusCode = 400;

        callback(statusCode);
    });
}

manager.getComment = (hubId, callback) => {
    let statusCode;
    let response = {
        comment: []
    }
    conn.query('select * from comment where hub_id= ?', hubId, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 201;
            for (var i = 0; i < rows.length; i++) {
                let comment = {
                    comment: rows[i].comment,
                    id: rows[i].id,
                    date: rows[i].date
                }
                response.comment.push(comment);
            }
        } else statusCode = 400;

        callback(statusCode, response);
    });
}

manager.getGood = (hubId, callback) => {
    let response = {
        good: null
    };
    let statusCode


    conn.query('select * from hub where hub_id=?', hubId, function (err, rows) {
        if (err) statusCode = 500;
        else if (rows.length == 1) {
            statusCode = 200;
            response.good=rows[0].good;
        }else statusCode=400;

        callback(statusCode, response);
    });
}

manager.hubRankListGood=(callback)=>{
    let response = {
        hub: []
    }
    let statusCode;

    conn.query('select * from hub order by good desc', null, function(err, rows){
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
            for(var i=0; i<rows.length; i++){
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
        }else statusCode=400;

        callback(statusCode, response);
    })
}

manager.hubRankListDate=(callback)=>{
    let response = {
        hub: []
    }
    let statusCode;

    conn.query('select * from hub order by date desc', null, function(err, rows){
        if (err) statusCode = 500;
        else if (rows.length >= 0) {
            statusCode = 200;
            for(var i=0; i<rows.length; i++){
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
        }else statusCode=400;

        callback(statusCode, response);
    })
}

module.exports = manager;