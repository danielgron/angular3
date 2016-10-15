var app = angular.module('carApp', ['ngRoute']);
app.config(function ($routeProvider) {
  $routeProvider
    .when("/add", {
      templateUrl: "views/addCar.html",
      controller: "AddController"
    })
    .when("/all", {
      templateUrl: "views/allCars.html",
      controller: "AllController"
    })
    
    .otherwise({
      redirectTo: "/all"
    });
});

app.factory('CarFactory',['$http', function ($http) {
 var getCars = function () {
    var cars=[];
  var promise = $http({
        
  method: 'GET',
  url: 'api/car'
});
  
  return promise;
 }; //Return Cars from the server
 var deleteCar = function (id) {
     $http({
  method: 'DELETE',
  url: 'api/car/'+id
});
     
    
 };//Delete Car on the Server
 var addCar = function(newcar){
     $http({
  method: 'PUT',
  url: 'api/car/',
  data: newcar
});
     
 };//Add Car on the Server
 var editCar = function(car){};//Edit Car on the Server;
 return {
 getCars: getCars,
 deleteCar: deleteCar,
 addCar: addCar,
 editCar: editCar
 };
}]);


var newcar;
app.controller('AllController',['$scope','CarFactory', function ($scope, CarFactory) {
$scope.predicate = "year";
$scope.reverse = false;
//$scope.cars =

$scope.getCars = function(){
   var prom=CarFactory.getCars();
   prom.then(function successCallback(response) {
    $scope.cars=response.data;
    console.log("Succes");
    console.log(response.data);
    
  },function errorfnct(response){console.log("Error"+response);});
  };
  
  $scope.getCars();
$scope.delete = function (id) {
        CarFactory.deleteCar(id);
        $scope.getCars();
  };
  
    $scope.edit = function (id) {
      newcar=$scope.cars[id];
  };

  }]);

app.controller('AddController', ['$scope','CarFactory',function ($scope,CarFactory) {

    $scope.newcar=newcar;

    
    $scope.save = function () {
    CarFactory.addCar($scope.newcar);
    $scope.newcar = {};
  };

  }]);

