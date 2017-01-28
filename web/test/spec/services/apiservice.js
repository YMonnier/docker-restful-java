'use strict';

describe('Service: apiService', function () {

  // load the service's module
  beforeEach(module('webApp'));

  // instantiate service
  var apiService;
  beforeEach(inject(function (_apiService_) {
    apiService = _apiService_;
  }));

  it('should do something', function () {
    expect(!!apiService).toBe(true);
  });

});
