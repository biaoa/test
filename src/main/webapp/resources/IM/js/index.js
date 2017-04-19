var demo = angular.module("demo", ["RongWebIMWidget"]);

demo.controller("main", ["$scope", "WebIMWidget","$http", function($scope,
  WebIMWidget,$http) {

  $scope.show = function() {
    WebIMWidget.show();
  }

  $scope.hidden = function() {
    WebIMWidget.hidden();
  }

  $scope.server = WebIMWidget;
  $scope.targetType=1;

  $scope.setconversation=function(){
    WebIMWidget.setConversation(Number($scope.targetType), $scope.targetId, "自定义:"+$scope.targetId);
  }
  angular.element(document).ready(function() {
    WebIMWidget.init({
      appkey: "pwe86ga5eoda6",
       token: token,
      style:{
        width:600,
        positionFixed:true,
        bottom:20,
      },
      displayConversationList:true,
      voiceUrl:'/resources/IM/images/sms-received.mp3',
      conversationListPosition:WebIMWidget.EnumConversationListPosition.right,
      onSuccess:function(){
      },
      onError:function(e){
    	    //初始化错误
    	  console.log(e);
    	  if(e.code==0){
    		 console.log("token失效,正在重新获取....")
    		  $http({
    	          method:'post',
    	          url:b+"/webIMtokenFail"
    	        }).success(function(data){
    	          if(data.code==0){
    	        	  window.location.reload();
    	          }
    	        })
    	  }
    	 }
    });

    WebIMWidget.show();
    
//    WebIMWidget.setUserInfoProvider(function(targetId,obj){
//        $http({
//          url:"/userinfo.json"
//        }).success(function(rep){
//          var user;
//          rep.userlist.forEach(function(item){
//            if(item.id==targetId){
//              user=item;
//            }
//          })
//
//          if(user){
//            obj.onSuccess({id:user.id,name:user.name,portraitUri:user.portraitUri});
//          }else{
//            obj.onSuccess({id:targetId,name:"陌："+targetId});
//          }
//        })
//    });
    WebIMWidget.setUserInfoProvider(function(targetId,obj){
    	console.log("调用setUserInfoProvider");
        $http({
          method:'GET',
          url:b+"/community/userInfo",
          params:{
              'userId':targetId
            }
        }).then(function(data){
        	retuslt = data.data.data;
        	if(retuslt.code==0){
        		console.log("这是获得的用户信息"+retuslt);
            	console.log(retuslt.name);
            	console.log(retuslt.id);
            	console.log(retuslt.portraitUri);
            	obj.onSuccess({name:retuslt.name,userId:retuslt.id,portraitUri:retuslt.portraitUri});
        	}
//          var user;
//          rep.userlist.forEach(function(item){
//        	  console.log(item);
//        	  
//            if(item.id==targetId){
//              user=item;
//            }
//          })
//          if(user){
//            obj.onSuccess({id:user.id,name:user.name,portraitUri:b+"/"+user.portraitUri});
//          }else{
//            obj.onSuccess({id:targetId,name:"陌："+targetId});
//          }
        })
    });

    WebIMWidget.setOnlineStatusProvider(function(arr,obj){
    	console.log("调用setOnlineStatusProvider");
        $http({
          method:'GET',
          url:b+"/community/online"
        }).success(function(data){
          obj.onSuccess(data.data);
        })
    })

    WebIMWidget.onClose=function(){
      console.log("已关闭");
    }

    WebIMWidget.show();


    //设置会话
    
//    WebIMWidget.setConversation("4277", "庞栋", "呵呵");


  });

}]);
