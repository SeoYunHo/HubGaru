"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');
let AES256 = require('nodejs-aes256');
let SHA256 = require('sha256');
let random = require('../../support/random');
const key = 'this_is_key';
let certifyString;

//회원가입
router.route('/account/signup').post(function (req, res) {
    let id = req.body.id;
    let name = req.body.name;
    let nick_name = req.body.nickname;
    let part = req.body.part;
    let email = req.body.email;
    let password = SHA256(req.body.password);
    let picture_url=req.body.picture_url;
    let user_intro=req.body.user_intro;

    console.log(id, password, email, part, user_intro, picture_url, nick_name, name);

    manager.signup(id, password, email, part, user_intro, picture_url, nick_name, name, function (response) {
        if (response.success) {
            res.writeHead(201, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(400, {
                'Content-Type': 'application/json'
            });
        }
        res.end();
    });
});

//인증번호 발송
router.route('/account/certify/demand/:phone').post(function (req, res) {
    let phone = req.params.phone;
    certifyString = random.randomString(6);

    let response = {
        certifyString: certifyString
    };

    if (!!response.certifyString) {
        res.writeHead(200, {
            'Content-Type': 'application/json'
        });
        res.write(JSON.stringify(response));
    }else{
         res.writeHead(204, {
            'Content-Type': 'application/json'
        });
        res.write(JSON.stringify(response));
    }
    res.end();
});

//인증번호 검증
router.route('/account/certify/verify/:certifyString').post(function (req, res) {
    let str = req.params.certifyString;

    if (str == certifyString) {
        res.writeHead(200, {
            'Content-Type': 'application/json'
        });
    } else {
        res.writeHead(400, {
            'Content-Type': 'application/json'
        });
    }
    res.end();
});

//로그인
router.route('/account/signin').post(function (req, res) {
    let id = req.body.id;
    let password = SHA256(req.body.password);

    manager.signin(id, password, function (response, message) {
        if (response.success) {
            req.session.user = {
                user_id: id,
                authorized: true
            };
            res.writeHead(201, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(400, {
                'Content-Type': 'application/json'
            });
        }
        if (!!message.message) {
            res.write(JSON.stringify(message));
            res.end();
        }
        res.end();

    });
});

//아이디 중복 체크
router.route('/account/idcheck').post(function (req, res) {
    let id = req.body.id;
    manager.idCheck(id, function (response) {
        console.log(response);
        if (response.overlap) {
            res.writeHead(200, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }
        res.end();
    });
});

//아이디 찾기
router.route('/account/findid').get(function (req, res) {
    let name = req.body.name;
    let phone = req.body.phone;

    manager.getId(name, phone, function (response) {
        if (!!response.id) {
            res.writeHead(200, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }

        res.write(JSON.stringify(response));
        res.end();
    });
});

//비밀번호 변경
router.route('/account/findpassword').put(function (req, res) {
    let id = req.params.id;
    let password = SHA256(req.body.password);

    manager.updatePassword(id, password, function (response) {
        if (response.success) {
            res.writeHead(201, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }
        res.end();
    });
});

module.exports = router;