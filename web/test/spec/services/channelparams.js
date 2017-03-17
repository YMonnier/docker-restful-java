'use strict';

describe('Service: ChannelParams', function () {

  // load the service's module
  beforeEach(module('webApp'));

  // instantiate service
  var ChannelParams;
  beforeEach(inject(function (_ChannelParams_) {
    ChannelParams = _ChannelParams_;
  }));

  it('should do something', function () {
    expect(!!ChannelParams).toBe(true);
  });

});
