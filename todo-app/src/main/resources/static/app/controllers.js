(function (angular) {
    var UserController = function ($scope, UserFactory) {
        UserFactory.query(function (response) {
            $scope.users = response ? response : [];
        });
    };

    var RegisterController = function ($scope, UserFactory) {
        $scope.user = {};

        $scope.register = function () {
            UserFactory.save($scope.user, function () {
                alert("success");
            }, function () {
                alert("error");
            });
        };
    };

    var TodoListController = function ($scope, TodoListFactory, $routeParams, $location) {
        var userId = $routeParams.userId;
        if (!userId) {
            $location.path('/');
        }
        TodoListFactory.query({
            userId: $routeParams.userId
        }, function (response) {
            $scope.todoList = response ? response : [];
        });
    };

    UserController.$inject = ['$scope', 'UserFactory'];
    RegisterController.$inject = ['$scope', 'UserFactory'];
    TodoListController.$inject = ['$scope', 'TodoListFactory', '$routeParams', '$location'];
    angular.module("myApp.controllers")
        .controller("UserController", UserController)
        .controller("RegisterController", RegisterController)
        .controller("TodoListController", TodoListController);
}(angular));