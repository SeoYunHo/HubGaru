"use strict";
let conn = require('../../DBConnection');

let manager = {}

manager.getHubDetail = () => {
    let response={
        hub:[]
    }
    let stateCode

    conn.query('select * from hub;', null, function (err, rows) {
        if (err) stateCode=500;
        else if (result.rows >= 0) {
            stateCode=200;
            for (let i = 0; i < rows.length; i++) {
                let hub = {
                    file_url: rows[i].file_url,
                    img: rows[i].img,
                    name: rows[i].name,
                    garuId: rows[i].garu_id
                }
                response.hub.push(hub);
            }
        }
        callback(stateCode, response);
    });
}

manager.getHub = () => {
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
                    file_url: rows[i].file_url,
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