package com.hhb.supermandict.model;

import java.util.ArrayList;

/**
 * Created by HoHoibin on 04/01/2018.
 * Email: imhhb1997@gmail.com
 */
//{
//        "msg": "SUCCESS",
//        "status_code": 0,
//        "data": {
//        "pronunciations": {
//        "uk": "wɜːd",
//        "us": "wɜːrd"
//        },
//        "en_definitions": {
//        "v": [
//        "put into words or an expression"
//        ],
//        "n": [
//        "a unit of language that native speakers can identify",
//        "a brief statement",
//        "information about recent and important events"
//        ]
//        },
//        "audio_addresses": {
//        "uk": [
//        "https://media-audio1.baydn.com/uk%2Fw%2Fwo%2Fword_v3.mp3",
//        "http://media-audio1.qiniu.baydn.com/uk/w/wo/word_v3.mp3"
//        ],
//        "us": [
//        "https://media-audio1.baydn.com/us%2Fw%2Fwo%2Fword_v3.mp3",
//        "http://media-audio1.qiniu.baydn.com/us/w/wo/word_v3.mp3"
//        ]
//        },
//        "uk_audio": "http://media.shanbay.com/audio/uk/word.mp3",
//        "conent_id": 312,
//        "audio_name": "word_v3",
//        "cn_definition": {
//        "pos": "",
//        "defn": "n. 词, 单词, 消息, 诺言\nvt. 用词语表达"
//        },
//        "num_sense": 1,
//        "content_id": 312,
//        "content_type": "vocabulary",
//        "sense_id": 0,
//        "id": 312,
//        "definition": " n. 词, 单词, 消息, 诺言\nvt. 用词语表达",
//        "has_collins_defn": true,
//        "has_oxford_defn": true,
//        "url": "https://www.shanbay.com/bdc/mobile/preview/word?word=word",
//        "has_audio": true,
//        "en_definition": {
//        "pos": "v",
//        "defn": "put into words or an expression"
//        },
//        "object_id": 312,
//        "content": "word",
//        "pron": "wɜːrd",
//        "pronunciation": "wɜːrd",
//        "id_str": "squjp",
//        "audio": "http://media.shanbay.com/audio/us/word.mp3",
//        "us_audio": "http://media.shanbay.com/audio/us/word.mp3"
//        }
//        }


public class WordModel {
    private String word;
    private Pronunciation pronunciation;
    private ArrayList<Definition> defs;

    public class Pronunciation {

        private String AmE;
        private String AmEmp3;
        private String BrE;
        private String BrEmp3;

        public String getAmE() {
            return AmE;
        }

        public void setAmE(String amE) {
            AmE = amE;
        }

        public String getAmEmp3() {
            return AmEmp3;
        }

        public void setAmEmp3(String amEmp3) {
            AmEmp3 = amEmp3;
        }

        public String getBrE() {
            return BrE;
        }

        public void setBrE(String brE) {
            BrE = brE;
        }

        public String getBrEmp3() {
            return BrEmp3;
        }

        public void setBrEmp3(String brEmp3) {
            BrEmp3 = brEmp3;
        }
    }

    public class Definition {

        private String pos;
        private String def;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getDef() {
            return def;
        }

        public void setDef(String def) {
            this.def = def;
        }
    }
}
