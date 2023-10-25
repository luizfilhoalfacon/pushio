module.exports = function(ctx) {
  // make sure android platform is part of build
  if (ctx.opts.platforms.indexOf('android') < 0) {
    return;
  }

  var fs = ctx.requireCordovaModule('fs');
  var path = ctx.requireCordovaModule('path');
  var deferral = ctx.requireCordovaModule('q').defer();

  var pushIOConfigFileName = 'pushio_config.json';
  var pushIOConfigFilePath = path.join(ctx.opts.projectRoot, pushIOConfigFileName);
  var newPushIOConfigFilePath = path.join(ctx.opts.projectRoot, 'platforms/android/assets', pushIOConfigFileName);

  fs.stat(pushIOConfigFilePath, function(error, stats) {
    if (error) {
      if (error.code == 'ENOENT') {
        deferral.reject('PushIO config file doesn\'t exist\'s (' + pushIOConfigFileName + ') - put it in your project root and build again');
        return;
      }

      throw error;
    }

    fs.createReadStream(pushIOConfigFilePath).pipe(fs.createWriteStream(newPushIOConfigFilePath));

    deferral.resolve();
  });

  return deferral.promise;
};
