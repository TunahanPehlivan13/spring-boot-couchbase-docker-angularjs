(function (angular) {
    var UserController = function ($scope, UserFactory) {
        UserFactory.query(function (response) {
            $scope.users = response ? response : [];
        });
    };

    var RegisterController = function ($scope, UserFactory, $location) {
        $scope.user = {};

        $scope.register = function () {
            UserFactory.save($scope.user, function () {
                alert("success");
                $location.path('/');
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
                controller: NoteDlgController,
                scope: $scope,
                resolve: {
                    todo: function () {
                        return $scope.todo;
                    }
                }
            });
        };
    };

    var NoteDlgController = function ($scope, TodoListFactory, todo) {
        $scope.saveNote = function () {
            TodoListFactory.save(todo, function () {
                $scope.todoList.push({
                    userId: todo.userId,
                    note: todo.note,
                    priority: todo.priority
                });
                $scope.cartDlg.close();
            }, function () {
                alert("error");
            });
        };
    };


    UserController.$inject = ['$scope', 'UserFactory'];
    RegisterController.$inject = ['$scope', 'UserFactory', '$location'];
    TodoListController.$inject = ['$scope', 'TodoListFactory', '$routeParams', '$location', '$modal'];
    NoteDlgController.$inject = ['$scope', 'TodoListFactory', 'todo'];
    angular.module("myApp.controllers")
        .controller("UserController", UserController)
        .controller("RegisterController", RegisterController)
        .controller("TodoListController", TodoListController);
}(angular));