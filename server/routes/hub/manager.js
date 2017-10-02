"use strict";
let conn = require('../../DBConnection');

let manager = {};

manager.checkId= (hubId) => {
    conn.query('select * from hub where hub_id',hubId, function(err, rows){
        if(err) return true;
        else if(rows.length==0) return false;
        else return true;
    });
}

manager.addHub = (hubId, garuId, file, name, img, date, callback) => {
    let stateCode;

    conn.query('insert into hub values (?,?,?,?,?,?,0);', [hubId, garuId, file, name, img, date], function(err, result){
        if(err) stateCode=500;
        else if(result.affectedRows) stateCode=204;
        callback(stateCode);
    });
}

manager.getHub = (callback) => {
    let response={
        hub:[]
    }
    let stateCode;

    conn.query('select * from hub;', null, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length >= 0) {
            stateCode=200;
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


    conn.query('select * from hub where garuid=?', garuId, function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length >= 0) {
            stateCode=200;
            for (let i = 0; i < rows.length; i++) {
                let hub = {
                    file: rows[i].file_url,
                    img: rows[i].img,
                    name: rows[i].name,
                    garuId: rows[i].garu_id
                }
                response.hub=hub;
            }
        }
        callback(stateCode, response);
    });

}

module.exports = manager;