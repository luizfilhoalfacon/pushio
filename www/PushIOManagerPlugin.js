var exec = require('cordova/exec');

var PushIOManager = (function() {
  var _plugin = {};
  var _pluginName = 'PushIOManagerPlugin';

  // contants for track engagement
  _plugin.PUSHIO_ENGAGEMENT_METRIC_LAUNCH = 1;
  _plugin.PUSHIO_ENGAGEMENT_METRIC_ACTIVE_SESSION = 2;
  _plugin.PUSHIO_ENGAGEMENT_METRIC_INAPP_PURCHASE = 3;
  _plugin.PUSHIO_ENGAGEMENT_METRIC_PREMIUM_CONTENT = 4;
  _plugin.PUSHIO_ENGAGEMENT_METRIC_SOCIAL = 5;
  _plugin.PUSHIO_ENGAGEMENT_METRIC_OTHER = 6;

  // native methods
  _plugin.registerUserId = function(userId) {
    exec(null, null, _pluginName, 'registerUserId', [userId]);
  }

  _plugin.unregisterId = function() {
    exec(null, null, _pluginName, 'unregisterId', []);
  }

  _plugin.getRegisteredUserId = function(success, error) {
    exec(success, error, _pluginName, 'getRegisteredUserId', []);
  };

  _plugin.getUUID = function(success, error) {
    exec(success, error, _pluginName, 'getUUID', []);
  };

  _plugin.getAPIKey = function(success, error) {
    exec(success, error, _pluginName, 'getAPIKey', []);
  };

  _plugin.registerCategories = function(categories) {
    exec(null, null, _pluginName, 'registerCategories', [categories]);
  };

  _plugin.unregisterCategories = function(categories) {
    exec(null, null, _pluginName, 'unregisterCategories', [categories]);
  };

  _plugin.registerCategory = function(category) {
    exec(null, null, _pluginName, 'registerCategory', [category]);
  };

  _plugin.trackEngagement = function(metric) {
    exec(null, null, _pluginName, 'trackEngagement', [metric]);
  };

  _plugin.resetEID = function() {
    exec(null, null, _pluginName, 'resetEID', []);
  };

  return _plugin;
})();

module.exports = PushIOManager;
