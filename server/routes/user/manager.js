"use strict";
let conn = require('../../DBConnection');
let AES256 = require('nodejs-aes256');
const key = 'this_is_key';

let manager = {}

//회원가입
manager.signup = function (id, name, part, password, user_intro, phone, callback) {
    let stateCode;

    conn.query('insert into account (id, password,part, user_intro, name, phone) values(?,?,?,?,?,?);', [id, password, part, user_intro, name, phone], function (err, result) {
        if (err) stateCode=500;
        else if (result.affectedRows) stateCode=201;

        callback(stateCode);
    });
}

//로그인
manager.signin = function (id, password, callback) {
    let stateCode;
    let message = {};

    conn.query('select * from account where id=?', id, function (err, rows) {
        console.log(rows);
        if (err) {
            stateCode=500;
            callback(stateCode, message);
        } else if (rows.length == 1) {
            conn.query('select * from account where id=? and password=?;', [id, password], function (err, rows1) {
                if (err) {
                    stateCode=500;
                    callback(stateCode, message);
                } else if (rows1.length == 1) {
                    stateCode=201;
                    callback(stateCode, message);
                } else if (rows1.length == 0) {
                    stateCode=204;
                    message.message = 'wrongPassword';
                    callback(stateCode, message);
                }
            });
        } else {
            stateCode=204;
            message.message = 'nonexistentId';
            callback(stateCode, message);
        }
    });
}

//아이디 중복 체크
manager.idCheck = function (id, callback) {
    let response = {
        overlap: false
    };
    conn.query('select * from account where id=?', id, function (err, rows) {
        if (err) response.error = true;
        else if (rows.length == 1) response.overlap = true;

        callback(response);
    });
}

//비밀번호 변경
manager.updatePassword = function (id, callback) {
    let stateCode

    conn.query('update account set password=? where id=?;', [id, password], function (err, result) {
        if (err) stateCode=500;
        else if (reslt.affectedRows) stateCode=201;

        callback(stateCode);
    });
}

//아이디 찾기
manager.getId = function (name, phone, callback) {
    let stateCode;
    let response = {
        id: null
    };

    conn.query('select id from account where name=? and phone=?;', [name, phone], function (err, rows) {
        if (err) stateCode=500;
        else if (rows.length == 1) {
            stateCode=200;
            response.id = rows[0].id;
        }
        callback(stateCode, response);
    });
}

module.exports = manager;