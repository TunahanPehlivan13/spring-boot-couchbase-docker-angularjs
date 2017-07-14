(function (angular) {
    angular.module("myApp.controllers", ["ui.bootstrap"]);
    angular.module("myApp.services", []);
    angular.module("myApp", ["ngResource", "myApp.controllers", "myApp.services", "ngRoute"])
        .config(['$routeProvider',
            function ($routeProvider) {
                $routeProvider.when('/', {
                    templateUrl: 'user-list.html',
                    controller: 'UserController'
                }).when('/register', {
                    templateUrl: 'register.html',
                    controller: 'RegisterController'
                }).when('/todo-list/:userId', {
                    templateUrl: 'todo-list.html',
                    controller: 'TodoListController'
                }).otherwise({
                    redirectTo: '/'
                });
            }]);
}(angular));