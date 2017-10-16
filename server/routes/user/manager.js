"use strict";
let conn = require('../../DBConnection');
let AES256 = require('nodejs-aes256');
const key = 'this_is_key';

let manager = {}

//회원가입
manager.signup = function (id, name, part, password, user_intro, phone, callback) {
    let statusCode;

    conn.query('insert into account (id, password,part, user_intro, name, phone) values(?,?,?,?,?,?);', [id, password, part, user_intro, name, phone], function (err, result) {
        if (err) statusCode=500;
        else if (result.affectedRows) statusCode=201;

        callback(statusCode);
    });
}

//로그인
manager.signin = function (id, password, callback) {
    let statusCode;
    let message = {};

    conn.query('select * from account where id=?', id, function (err, rows) {
        console.log(rows);
        if (err) {
            statusCode=500;
            callback(statusCode, message);
        } else if (rows.length == 1) {
            conn.query('select * from account where id=? and password=?;', [id, password], function (err, rows1) {
                if (err) {
                    statusCode=500;
                    callback(statusCode, message);
                } else if (rows1.length == 1) {
                    statusCode=201;
                    callback(statusCode, message);
                } else if (rows1.length == 0) {
                    statusCode=204;
                    message.message = 'wrongPassword';
                    callback(statusCode, message);
                }
            });
        } else {
            statusCode=204;
            message.message = 'nonexistentId';
            callback(statusCode, message);
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
manager.updatePassword = function (id, password, callback) {
    let statusCode

    conn.query('update account set password=? where id=?;', [password, id], function (err, result) {
        if (err) statusCode=500;
        else if (result.affectedRows) statusCode=201;

        callback(statusCode);
    });
}

//아이디 찾기
manager.getId = function (name, phone, callback) {
    let statusCode;
    let response = {
        id: null
    };

    conn.query('select id from account where name=? and phone=?;', [name, phone], function (err, rows) {
        if (err) statusCode=500;
        else if (rows.length == 1) {
            statusCode=200;
            response.id = rows[0].id;
        }
        callback(statusCode, response);
    });
}

module.exports = manager;