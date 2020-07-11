<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<body>

<script>
var app = angular.module("wordlist", []); 
app.controller("myCtrl", function($scope, $http) {
	
	$http.get("webapi/word")
    .then(function (response) {$scope.words = response.data;}, 
    		function error(response) {
    	$scope.errortext = response.status + ": " + response.statusText;
	});
	  	
	$scope.searchItem = function() {
    	$scope.errortext = "";
    	if (!$scope.addMe) {return;}
    	icount = -1;
    	    
    	$http.get("webapi/word/" + $scope.addMe)
        .then(function (response) {
        	$scope.words = response.data;
        	if($scope.addMe.length > 0 && $scope.words.length == 0){
        		document.getElementById("addItem").disabled = false;
        	}	
        	else{
        		document.getElementById("addItem").disabled = true;
        	}
        }, 
        		function error(response) {
        	$scope.errortext = response.status + ": " + response.statusText;
    	});
    };
    
	
	$scope.addItem = function() {
    	$scope.errortext = "";
    	if (!$scope.addMe) {return;}
    	icount = -1;
    	
    	for(i=0; i < $scope.words.length; i++){
    		if($scope.words[i].word.trim() == $scope.addMe.trim()){
    			icount++;
    			break;
    		}
    	}
    	
    	if (icount > -1) {
   			$scope.errortext = "The word is already available in the list.";
       	}
    	else {
   			var data = {
            		word: $scope.addMe
            };
            
            $http.post('webapi/word/save', JSON.stringify(data))
               .then(function (response) {
                if(response.data == "Done"){
                	$scope.errortext = "Word added in database";
                	$scope.addMe = "";
                	$http.get("webapi/word")
                    .then(function (response) {$scope.words = response.data;}, 
                    		function error(response) {
                    	$scope.errortext = response.data;
              	  	});
                }
                else{
                	$scope.errortext = response.data;
                }
                
              }, 
              function (response) {
            	  $scope.errortext = response.status + ": " + response.statusText;
              });
        }
    };
    
    $scope.removeItem = function (x) {
    	$scope.errortext = "";    
    	$scope.addMe = "";
        if(confirm("Do you want to really delete the file?")){
        	$http.post('webapi/word/del', JSON.stringify($scope.words[x]))
            .then(function (response) {
            	if(response.data == "Done"){
                	$scope.errortext = "Data deleted";
                	
                	$scope.words.splice(x, 1);
                	
                	$http.get("webapi/word")
                    .then(function (response) {$scope.words = response.data;}, 
                    		function error(response) {
                    	$scope.errortext = response.data;
              	  	});
                }
                else{
                	$scope.errortext = response.data;
                } 
    	       }, 
    	       function (response) {
             	  $scope.errortext = response.status + ": " + response.statusText;
               });
    	  }
    }
});
</script>

<div ng-app="wordlist" ng-cloak ng-controller="myCtrl" class="w3-card-2 w3-margin" style="max-width:400px;">
  <header class="w3-container w3-light-grey w3-padding-16">
    <h3>My word List</h3>
  </header>
  <div class="w3-container w3-light-grey w3-padding-16">
    <div class="w3-row w3-margin-top">
      <div class="w3-col s10">
        <input ng-placeholder="Add word here" ng-change="searchItem()" ng-model="addMe" class="w3-input w3-border w3-padding">
      </div>
      <div class="w3-col s2">
        <button ng-click="addItem()" id="addItem" class="w3-btn w3-padding w3-green" disabled="disabled">Add</button>
      </div>
    </div>
    <p class="w3-text-red">{{errortext}}</p>
  </div>
  <ul class="w3-ul">
    <li ng-repeat="x in words" class="w3-padding-16">{{x.word}}<span ng-click="removeItem($index)" style="cursor:pointer;" class="w3-right w3-margin-right">×</span></li>
  </ul>
</div>

</body>
</html>
