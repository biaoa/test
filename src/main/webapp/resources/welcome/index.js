$(function(){
    $('#dowebok').fullpage({
         'navigation': true,
        'loopBottom':true,
        'loopTop':true,
        'resize':true,
        // 'scrollOverflow':true,
        'afterLoad': function(anchorLink, index){
                if(index == 2){
                    
                   $(".hide").removeAttr("class").attr("class", "mobi img-responsive animated slideInLeft");
               
                 



                }
                
            },

       'onLeave':function(index,nextindex,up){
            
            if(index == 2){
                    
                   $(".mobi").removeAttr("class").attr("class", "hide");
               
                 



                }

       },


    });

   $(window).resize(function(){
        autoScrolling();
    });

    function autoScrolling(){
        var $ww = $(window).width();
        if($ww < 1400){
            $.fn.fullpage.setAutoScrolling(false);
        } else {
            $.fn.fullpage.setAutoScrolling(true);
        }
    }

    autoScrolling();
   
   // $.fn.fullpage.setAutoScrolling(false);



});