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

    var TodoListController = function ($scope, TodoListFactory, $routeParams, $location, $modal) {
        var userId = $routeParams.userId;
        $scope.todo = {userId: userId, priority: 'LOW'};

        if (!userId) {
            $location.path('/');
        }
        TodoListFactory.query({
            userId: $routeParams.userId
        }, function (response) {
            $scope.todoList = response ? response : [];
        });

        $scope.openNoteDlg = function () {
            $scope.cartDlg = $modal.open({
                templateUrl: 'note-dialog.html',
                controller: TodoListController,
                scope: $scope,
                resolve: {
                    userId: function () {
                        return userId;
                    }
                }
            });
        };

        $scope.saveNote = function () {
            TodoListFactory.save($scope.todo, function () {
                alert("success");
            }, function () {
                alert("error");
            });
        };
    };

    UserController.$inject = ['$scope', 'UserFactory'];
    RegisterController.$inject = ['$scope', 'UserFactory'];
    TodoListController.$inject = ['$scope', 'TodoListFactory', '$routeParams', '$location', '$modal'];
    angular.module("myApp.controllers")
        .controller("UserController", UserController)
        .controller("RegisterController", RegisterController)
        .controller("TodoListController", TodoListController);
}(angular));