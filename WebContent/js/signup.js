/*
# Copyright  (c) 2015-2016, Cobweb IO (http://www.cobweb.io) All Rights Reserved.
#   
# Cobweb IO licences this file to you under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
*/
var app = angular.module('CobWebApp', ['ngDialog']);

app.controller('LoginCtrl', ['$scope', '$http', 'ngDialog', function($scope, $http, ngDialog) {
  console.log("login ctrl fired");

  $scope.signup = function(user) {

    var userData = {
      firstname: user.firstname,
      lastname: user.lastname,
      email: user.email,
      password: user.password
    };

    if ($scope.signupForm.$valid) {
      $http.post('/anon/signup', userData).
      then(function(response) {
        console.log(response);
        ngDialog.open({ template: '<p> An activation link has been sent. Please check your email and verify in order to activate. </p> <button ng-click="closeThisDialog()" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Ok</button>', className: 'ngdialog-theme-default', scope: $scope, plain: true});
      });
    }
  }
}]);

app.directive('compareTo', [function () {
  return {
    require: "ngModel",
    scope: {
        otherModelValue: "=compareTo"
    },
    link: function(scope, element, attributes, ngModel) {

        ngModel.$validators.compareTo = function(modelValue) {
            return modelValue == scope.otherModelValue;
        };

        scope.$watch("otherModelValue", function() {
            ngModel.$validate();
        });
    }
  };
}]);

