package com.hhb.supermandict.model;

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
//        "pronunciations": "wɜːrd",
//        "id_str": "squjp",
//        "audio": "http://media.shanbay.com/audio/us/word.mp3",
//        "us_audio": "http://media.shanbay.com/audio/us/word.mp3"
//        }
//        }


public class ShanBayModel<T> {

    public String msg;
    public int status_code;
    public T data;

    public static class ShanBay {
        private Pronunciations pronunciations;
        private String uk_audio;
        private String us_audio;


        private Definition cn_definition;



        private Definition en_definition;

        public class Pronunciations {
            private String uk;
            private String us;

            public String getUk() {
                return uk;
            }

            public void setUk(String uk) {
                this.uk = uk;
            }

            public String getUs() {
                return us;
            }

            public void setUs(String us) {
                this.us = us;
            }
        }



        public class Definition {
            private String pos;
            private String defn;

            public String getPos() {
                return pos;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public String getDefn() {
                return defn;
            }

            public void setDefn(String defn) {
                this.defn = defn;
            }
        }

        public Definition getCn_definition() {
            return cn_definition;
        }

        public void setCn_definition(Definition cn_definition) {
            this.cn_definition = cn_definition;
        }

        public void setEn_definition(Definition en_definition) {
            this.en_definition = en_definition;
        }

        public Definition getEn_definition() {
            return en_definition;
        }

        public Pronunciations getPronunciations() {
            return pronunciations;
        }

        public void setPronunciations(Pronunciations pronunciations) {
            this.pronunciations = pronunciations;
        }

        public String getUk_audio() {
            return uk_audio;
        }

        public void setUk_audio(String uk_audio) {
            this.uk_audio = uk_audio;
        }

        public String getUs_audio() {
            return us_audio;
        }

        public void setUs_audio(String us_audio) {
            this.us_audio = us_audio;
        }

    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
