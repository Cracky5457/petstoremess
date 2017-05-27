'use strict';

describe('Service: petsApiFactory', function () {

  // load the service's module
  beforeEach(module('petstoreFrontApp'));

  // instantiate service
  var petsApiFactory;
  beforeEach(inject(function (_petsApiFactory_) {
    petsApiFactory = _petsApiFactory_;
  }));

  it('should do something', function () {
    expect(!!petsApiFactory).toBe(true);
  });

});
