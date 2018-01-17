cordova.define("TalkingData.TalkingDataSMS", function(require, exports, module) {
var TalkingDataSMS = {
    initEAuth:function(appKey, secretId, accountName) {
        Cordova.exec(null, null, "TalkingDataSMS", "initEAuth", [appKey, secretId, accountName]);
    },
    applyAuthCode:function(countryCode, mobile, accountName, succCallback, failedCallback) {
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "applyAuthCode", [countryCode, mobile, accountName]);
    },
    reapplyAuthCode:function(countryCode, mobile, requestId, accountName, succCallback, failedCallback) {
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "reapplyAuthCode", [countryCode, mobile, requestId, accountName]);
     },
    isVerifyAccount:function(accountName, succCallback, failedCallback){
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "isVerifyAccount", [accountName]);
    },
    isMobileMatchAccount:function(countryCode, mobile, accountName, succCallback, failedCallback){
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "isMobileMatchAccount", [countryCode, mobile, accountName]);
    },
    bindEAuth:function(countryCode, mobile, authCode, accountName, succCallback, failedCallback){
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "bindEAuth", [countryCode, mobile, authCode, accountName]);
    },
    unbindEAuth:function(countryCode, mobile, accountName, succCallback, failedCallback){
        Cordova.exec(succCallback, failedCallback, "TalkingDataSMS", "unbindEAuth", [countryCode, mobile, accountName]);
    },
    getDeviceId:function(callBack) {
        Cordova.exec(callBack, null, "TalkingDataSMS", "getDeviceId", []);
    }
   };

module.exports = TalkingDataSMS;

});
