    "use strict";

    //General function for all pages

    //Modernizr touch detect

    // jQuery(function($){
    //     $.datepicker.regional['zh-CN'] = {
    //         closeText: '关闭',
    //         prevText: '&#x3c;上月',
    //         nextText: '下月&#x3e;',
    //         currentText: '今天',
    //         monthNames: ['一月','二月','三月','四月','五月','六月',
    //           '七月','八月','九月','十月','十一月','十二月'],
    //         monthNamesShort: ['一','二','三','四','五','六',
    //           '七','八','九','十','十一','十二'],
    //         dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
    //         dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
    //         dayNamesMin: ['日','一','二','三','四','五','六'],
    //         weekHeader: '周',
    //         dateFormat: 'yy-mm-dd',
    //         firstDay: 1,
    //         isRTL: false,
    //         showMonthAfterYear: true,
    //         yearSuffix: '年'};
    //     $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    //   });
    Modernizr.load({
            test: Modernizr.touch,
            yep :['css/touch.css?v=1'],
            nope: [] 
    });

	//1. Scroll to top arrow
	// Scroll to top
        var $block =$('<div/>',{'class':'top-scroll'}).append('<a href="#"/>').hide().appendTo('body').click(function () {
            $('body,html').animate({scrollTop: 0}, 800);
            return false;
        });
                  
        //initialization
        var $window = $(window);

        if ($window.scrollTop() > 35) {showElem();} 
        else {hideElem();}

        //handlers    
        $window.scroll(function () {    
            if ($(this).scrollTop() > 35) {showElem();} 
            else {hideElem();}
        });

        //functions
        function hideElem(){
            $block.fadeOut();
        }   
        function showElem(){
            $block.fadeIn();
        }

    //2. Mobile menu
    //Init mobile menu
    $('#navigation').mobileMenu({
        triggerMenu:'#navigation-toggle',
        subMenuTrigger: ".sub-nav-toggle",
        animationSpeed:500  
    });

    //3. Search bar dropdown
    //search bar
    $("#search-sort").selectbox({
            onChange: function (val, inst) {

                $(inst.input[0]).children().each(function(item){
                    $(this).removeAttr('selected');
                })
                $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
            }

        });

    //4. Login window pop up
    //Login pop up
    $('.login-window').click(function (e){
        e.preventDefault();
        $('.overlay').removeClass('close').addClass('open');
    });

    $('.overlay-close').click(function (e) {
        e.preventDefault;
        $('.overlay').removeClass('open').addClass('close');

        setTimeout(function(){
            $('.overlay').removeClass('close');}, 500);
    });

function init_Elements () {
    "use strict";

	//1. Accodions
	//Init 2 type accordions
        $('#accordion').collapse();
        $('#accordion-dark').collapse();

    //2. Dropdown
    //select
    $("#select-sort").selectbox({
            onChange: function (val, inst) {

                $(inst.input[0]).children().each(function(item){
                    $(this).removeAttr('selected');
                })
                $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
            }

        });

            

    //3. Datapicker init
    $( ".datepicker__input" ).datepicker({
        showOtherMonths: true,
        selectOtherMonths: true,
        showAnim:"fade"
    });

    $(document).click(function(e) { 
        var ele = $(e.target); 
        if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
             $(".datepicker__input").datepicker("hide");

        }
   });

   //4. Tabs
   //Init 2 type tabs
    $('#hTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });

    $('#vTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    }); 

    //5. Mega select with filters
    //Mega select interaction
            $('.mega-select__filter').click(function(e){
                //prevent the default behaviour of the link
                e.preventDefault();
                $('.select__field').val('');
   
                $('.mega-select__filter').removeClass('filter--active');
                $(this).addClass('filter--active');
                
                //get the data attribute of the clicked link(which is equal to value filter of our content
                var filter = $(this).attr('data-filter');

                //Filter buttoms
                //show all the list items(this is needed to get the hidden ones shown)
                $(".select__btn a").show();
                
                /*using the :not attribute and the filter class in it we are selecting
                only the list items that don't have that class and hide them '*/
                $('.select__btn a:not(.' + filter + ')').hide();

                //Filter dropdown
                //show all the list items(this is needed to get the hidden ones shown)
                $(".select__group").removeClass("active-dropdown");
                $(".select__group").show();
                
                /*using the :not attribute and the filter class in it we are selecting
                only the list items that don't have that class and hide them '*/
                $('.select__group.' + filter).addClass("active-dropdown");
                $('.select__group:not(.' + filter + ')').hide();
            });

             $('.filter--active').trigger('click');
                
                
            
            $('.select__field').focus(function() {
                $(this).parent().find('.active-dropdown').css("opacity", 1);
            });

            $('.select__field').blur(function() {
                $(this).parent().find('.active-dropdown').css("opacity", 0);
            });


            $('.select__variant').click( function () {
                var value = $(this).attr('data-value');

                $('.select__field').val(value);
            });

    //6. Progressbar
    		//Count function for progressbar
    		function init_progressBar(duration) {
                    $('.progress').each(function() {
                        var value = $(this).find('.progress__bar').attr('data-level');
                        var result = value + '%';
                        if(duration) {
                            $(this).find('.progress__current').animate({width : value + '%'}, duration);
                        }
                        else {
                            $(this).find('.progress__current').css({'width' : value + '%'});
                        }
                        
                    });
            }

            //inview progress bars
            $('.progress').one('inview', function (event, visible) {
                if (visible == true) {
                      init_progressBar(2000);
                }
            });

    //7. Dropdown for authorize button
    		//user list option
            $('.auth__show').click(function (e){
                e.preventDefault();
                $('.auth__function').toggleClass('open-function')
            })

            $('.btn--singin').click(function (e){
                e.preventDefault();
                $('.auth__function').toggleClass('open-function')
            });

}

function init_Home() {
    "use strict";

	//1. Init revolution slider and add arrows behaviour
				var api = $('.banner').revolution({
                    delay:9000,
                    startwidth:1170,
                    startheight:500,
             
                     onHoverStop:"on",
             
                     hideArrowsOnMobile:"off",

                     hideTimerBar:"on",
                     hideThumbs:'0',
             
                     keyboardNavigation:"on",
             
                     navigationType:"none",
                     navigationArrows:"solo",
             
                     soloArrowLeftHalign:"left",
                     soloArrowLeftValign:"center",
                     soloArrowLeftHOffset:0,
                     soloArrowLeftVOffset:0,
             
                     soloArrowRightHalign:"right",
                     soloArrowRightValign:"center",
                     soloArrowRightHOffset:0,
                     soloArrowRightVOffset:0,
             
             
                     touchenabled:"on",
                     swipe_velocity:"0.7",
                     swipe_max_touches:"1",
                     swipe_min_touches:"1",
                     drag_block_vertical:"false",
             
             
                     fullWidth:"off",
                     forceFullWidth:"off",
                     fullScreen:"off",
             
                  });

                    api.bind("revolution.slide.onchange",function (e,data) {
                         var slides = $('.banner .slide');
                         var currentSlide= data.slideIndex;

                         var nextSlide = slides.eq(currentSlide).attr('data-slide');
                         var prevSlide = slides.eq(currentSlide-2).attr('data-slide');

                         var lastSlide = slides.length;

                         if(currentSlide == lastSlide) {
                             var nextSlide = slides.eq(0).attr('data-slide');
                         }

                         //put onload value for slider navigation
                        $('.tp-leftarrow').html( '<span class="slider__info">' + prevSlide + '</span>');
                        $('.tp-rightarrow').html( '<span class="slider__info">' + nextSlide + '</span>');

                    });

	
	//2. Dropdown for authorize button
    		//user list option
            $('.auth__show').click(function (e){
                e.preventDefault();
                $('.auth__function').toggleClass('open-function')
            })

            $('.btn--singin').click(function (e){
                e.preventDefault();
                $('.auth__function').toggleClass('open-function')
            });

    //3. Mega select with filters (and markers)
    //Mega select interaction
                    $('.mega-select__filter').click(function(e){
                        //prevent the default behaviour of the link
                        e.preventDefault();
                        $('.select__field').val('');
           
                        $('.mega-select__filter').removeClass('filter--active');
                        $(this).addClass('filter--active');
                        
                        //get the data attribute of the clicked link(which is equal to value filter of our content)
                        var filter = $(this).attr('data-filter');

                        //Filter buttons
                        //show all the list items(this is needed to get the hidden ones shown)
                        $(".select__btn a").show();
                        $(".select__btn a").css('display', 'inline-block');
                        
                        /*using the :not attribute and the filter class in it we are selecting
                        only the list items that don't have that class and hide them '*/
                        $('.select__btn a:not(.' + filter + ')').hide();

                        //Filter dropdown
                        //show all the list items(this is needed to get the hidden ones shown)
                        $(".select__group").removeClass("active-dropdown");
                        $(".select__group").show();
                        
                        /*using the :not attribute and the filter class in it we are selecting
                        only the list items that don't have that class and hide them '*/
                        $('.select__group.' + filter).addClass("active-dropdown");
                        $('.select__group:not(.' + filter + ')').hide();

                        //Filter marker
                        //show all the list items(this is needed to get the hidden ones shown)
                        $(".marker-indecator").show();
                        
                        /*using the :not attribute and the filter class in it we are selecting
                        only the list items that don't have that class and hide them '*/
                        $('.marker-indecator:not(.' + filter + ')').hide();                        
                    });

                    $('.filter--active').trigger('click');
                    $('.active-dropdown').css("z-index", '-1');
            
                    $('.select__field').focus(function() {
                        $(this).parent().find('.active-dropdown').css("opacity", 1);
                        $(this).parent().find('.active-dropdown').css("z-index", '50');
                    });

                    $('.select__field').blur(function() {
                        $(this).parent().find('.active-dropdown').css("opacity", 0);
                        $(this).parent().find('.active-dropdown').css("z-index", '-1');
                    });

                    $('.select__variant').click( function (e) {
                        e.preventDefault();
                        $(this).parent().find('.active-dropdown').css("z-index", '50');
                        var value = $(this).attr('data-value');
                        $('.select__field').val(value);
                        $(this).parent().find('.active-dropdown').css("z-index", '-1');
                    });

                    $('body').click( function (e){
                      console.log(e.target);
                    })

    //4. Rating scrore init
    //Rating star
    $('.score').raty({
        width:130, 
        score: 0,
        path: 'images/rate/',
        starOff : 'star-off.svg',
        starOn  : 'star-on.svg' 
    });

    //5. Scroll down navigation function
    //scroll down
    $('.movie-best__check').click(function (ev) {
        ev.preventDefault();
        $('html, body').stop().animate({'scrollTop': $('#target').offset().top-30}, 900, 'swing');
    });
}

function init_BookingOne() {
    "use strict";
    axios.post('http://localhost:8081/moive/Fliter/details',{
            "state":"on",
            "pageNumber": 0,
            "pageSize": 100,
            })
            .then((response)=> {
                console.log(response);
                var j,len;
                var mlist=response.data.data.cinemas_infos;
                var movie_list=[];
                for(j = 0,len=mlist.length; j < len; j++) {
                    var m=mlist[j];
                    var mid=m.id;                           
                    var temp={src:'movie-page-full.html',imgsrc:'images/movie/'+mid+'/post.jpg',title:m.name,time:m.duration,rating:m.score,id:mid};

                    movie_list.push(temp);
                }
                $("#demo1").tmpl(movie_list).appendTo('.swiper-wrapper');  
                init_BookingOne_2();
            })
            .catch(function (error) {
                console.log(error);
            });
	
}
function init_BookingOne_2(){
    //1. Buttons for choose order method
	//order factor
    $('.order__control-btn').click(function (e) {
        e.preventDefault();
        var state="on";
        $('.order__control-btn').removeClass('active');
        $(this).addClass('active');
        if($(this).text()=="购票"){
            state="on";
        }else{
            state="pre";
        }
        axios.post('http://localhost:8081/moive/Fliter/details',{
            "state":state,
            "pageNumber": 0,
            "pageSize": 100,
            })
            .then((response)=> {
                var j,len;
                var mlist=response.data.data.cinemas_infos;
                var movie_list=[];
                for(j = 0,len=mlist.length; j < len; j++) {
                    var m=mlist[j];
                    var mid=m.id;                           
                    var temp={src:'movie-page-full.html',imgsrc:'images/movie/'+mid+'/post.jpg',title:m.name,time:m.duration,option:'科幻｜剧情',rating:m.score,id:mid};

                    movie_list.push(temp);
                    console.log(movie_list);
                }
                $('.swiper-wrapper').empty();
                $("#demo1").tmpl(movie_list).appendTo('.swiper-wrapper');  
                init_BookingOne_2();
            })
            .catch(function (error) {
                console.log(error);
            });
    })

    //2. Init vars for order data
    // var for booking;
                var movie = $('.choosen-movie'),
                    city = $('.choosen-city'),
                    date = $('.choosen-date'),
                    cinema = $('.choosen-cinema'),
                    time = $('.choosen-time');

    //3. Swiper slider
    //init employee sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:10,
                    loop:true
                  });

                $('.swiper-slide-active').css({'marginLeft':'-2px'});
                //media swipe visible slide
                //Onload detect
                    if ($(window).width() > 1930 ){
                         mySwiper.params.slidesPerView=13;
                         mySwiper.resizeFix();         
                    }else

                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.resizeFix();         
                    }
                    else
                    if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.resizeFix();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                         mySwiper.resizeFix();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();    
                    }

                    else{
                        mySwiper.params.slidesPerView=10;
                        mySwiper.resizeFix();
                    }

                //Resize detect
                $(window).resize(function(){
                    if ($(window).width() > 1930 ){
                         mySwiper.params.slidesPerView=13;
                         mySwiper.reInit();          
                    }

                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.reInit();          
                    }
                    else
                     if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.reInit();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                          mySwiper.reInit();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();   
                    }

                    else{
                        mySwiper.params.slidesPerView=10;
                        mySwiper.reInit();
                    }
                 });
	
	//4. Dropdown init 
				//select
                $("#select-sort").selectbox({
                        onChange: function (val, inst) {

                            $(inst.input[0]).children().each(function(item){
                                $(this).removeAttr('selected');
                            })
                            $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
                        }

                    });

    
    //5. Datepicker init
                $( ".datepicker__input" ).datepicker({
                  showOtherMonths: true,
                  selectOtherMonths: true,
                  showAnim:"fade",
                  onSelect: function(selectedDate){
                       var mid= $('.film--choosed a').attr('href').split('=')[1];
                       var date=$('#datepicker').attr('value');
                       axios.get('http://localhost:8081/cinema/movieScheduals',{params:{movie_id:mid,date:date}})
                       .then((response)=>{
                           if(response.data.data!=null){
                               var tlist=response.data.data.cinema_infos;
                               console.log(tlist);
                               var p=[];
                               for(var t in tlist){
                                   var items=[];
                                   for(var s in tlist[t].sched_infos){
                                       var item=[];
                                       item.push(tlist[t].sched_infos[s].sched_id);
                                       item.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
                                       items.push(item);
                                   }
                                   var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
                                   p.push(k);
                               }
                               console.log(p);
                               $(".time-select").empty();
                               $("#demo3").tmpl(p).appendTo(".time-select");  
                               //choose time
                               $('.time-select__item').click(function (){
                                   //visual iteractive for choose
                                   $('.time-select__item').removeClass('active');
                                   $(this).addClass('active');
   
                                   //data element init
                                   var chooseTime = $(this).attr('data-time');
                                   $('.choose-indector--time').find('.choosen-area').text(chooseTime);
   
                                   //data element init
                                   var chooseCinema = $(this).parent().parent().find('.time-select__place').text(); 
   
                                   //data element set
                                   time.val(chooseTime);
                                   cinema.val(chooseCinema);
                                   
                                   var link='book2.html?schedid='+$(this).attr('name');
   
                                    $('.booking-pagination__next').attr('href',link);
                               });}
                       })
                       .catch(function (error) {
                           console.log(error);
                       });    
                  }
                });

                $(document).click(function(e) { 
                    var ele = $(e.target); 
                    if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
                       $(".datepicker__input").datepicker("hide");
                     }
                });

	//6. Choose variant proccess
				//choose film
                $('.film-images').click(function (e) {
                	 //visual iteractive for choose
                     $('.film-images').removeClass('film--choosed');
                     $(this).addClass('film--choosed');

                     //data element init
                     var chooseFilm = $(this).parent().attr('data-film');
                     $('.choose-indector--film').find('.choosen-area').text(chooseFilm);

                     //data element set
                     movie.val(chooseFilm);

                     $('.choose-indector--time span').empty();

                    var mid= $('.film--choosed a').attr('href').split('=')[1];
                    var date=$('#datepicker').attr('value');
                    axios.get('http://localhost:8081/cinema/movieScheduals',{params:{movie_id:mid,date:date}})
                    .then((response)=>{
                        if(response.data.data!=null){
                            var tlist=response.data.data.cinema_infos;
                            console.log(tlist);
                            var p=[];
                            for(var t in tlist){
                                var items=[];
                                for(var s in tlist[t].sched_infos){
                                    var item=[];
                                    item.push(tlist[t].sched_infos[s].sched_id);
                                    item.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
                                    items.push(item);
                                }
                                var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
                                p.push(k);
                            }
                            console.log(p);
                            $(".time-select").empty();
                            $("#demo3").tmpl(p).appendTo(".time-select");  
                            //choose time
                            $('.time-select__item').click(function (){
                                //visual iteractive for choose
                                $('.time-select__item').removeClass('active');
                                $(this).addClass('active');

                                //data element init
                                var chooseTime = $(this).attr('data-time');
                                $('.choose-indector--time').find('.choosen-area').text(chooseTime);

                                //data element init
                                var chooseCinema = $(this).parent().parent().find('.time-select__place').text(); 

                                //data element set
                                time.val(chooseTime);
                                cinema.val(chooseCinema);
                                
                                var link='book2.html?schedid='+$(this).attr('name');

                                 $('.booking-pagination__next').attr('href',link);
                            });

                            // choose (change) city and date for film

                            //data element init (default)
                            var chooseCity = $('.select .sbSelector').text();
                            var chooseDate = $('.datepicker__input').val();

                            //data element set (default)
                            city.val(chooseCity);
                            date.val(chooseDate);


                            $('.select .sbOptions').click(function (){
                                //data element change
                                var chooseCity = $('.select .sbSelector').text();
                                //data element set change
                                city.val(chooseCity);
                            });

                            $('.datepicker__input').change(function () {
                                //data element change
                                var chooseDate = $('.datepicker__input').val();
                                //data element set change
                                date.val(chooseDate);
                            });

                            // --- Step for data - serialize and send to next page---//
                            $('.booking-form').submit( function () {
                                var bookData = $(this).serialize();
                                $.get( $(this).attr('action'), bookData );
                            })

                //7. Visibility block on page control
                            //control block display on page
                            $('.choose-indector--film').click(function (e) {
                                e.preventDefault();
                                $(this).toggleClass('hide-content');
                                $('.choose-film').slideToggle(400);
                            })

                            $('.choose-indector--time').click(function (e) {
                                e.preventDefault();
                                $(this).toggleClass('hide-content');
                                $('.time-select').slideToggle(400);
                            })
                        }
                        
                    })
                    .catch(function (error) {
                        console.log(error);
                    });      

                })

                

                
}

function init_BookingTwo_2 () {
    //1. Buttons for choose order method
	//order factor
    $('.order__control-btn').click(function (e) {
        e.preventDefault();

        $('.order__control-btn').removeClass('active');
        $(this).addClass('active');
    })

    //2. Init vars for order data
    // var for booking;
                var numberTicket = $('.choosen-number'),
                    sumTicket = $('.choosen-cost'),
                    cheapTicket = $('.choosen-number--cheap'),
                    middleTicket = $('.choosen-number--middle'),
                    expansiveTicket = $('.choosen-number--expansive'),
                    sits = $('.choosen-sits');

    //3. Choose sits (and count price for them)
    			//users choose sits

    			//data elements init
                var sum = 0;
                var cheap = 0;
                var middle = 0;
                var expansive = 0;

                $('.sits__place').click(function (e) {
                    var ss="";
                    e.preventDefault();
                    var place = $(this).attr('data-place');
                    var ticketPrice = $(this).attr('data-price');

                    if(! $(e.target).hasClass('sits-state--your')){

                        if (! $(this).hasClass('sits-state--not') ) {
                            $(this).addClass('sits-state--your');

                            $('.checked-place').prepend('<span class="choosen-place '+place+'">'+ place +'</span>');

                            switch(ticketPrice)
                                {
                                case '10':
                                  sum += 10;
                                  cheap += 1;
                                  break;
                                case '20':
                                  sum += 20;
                                  middle += 1;
                                  break;
                                case '50':
                                  sum += 50;
                                  expansive += 1;
                                  break;
                            }

                            $('.checked-result').text('¥'+sum);
                        }
                    }

                    else{
                        $(this).removeClass('sits-state--your');
                        
                        $('.'+place+'').remove();

                        switch(ticketPrice)
                                {
                                case '10':
                                  sum -= 10;
                                  cheap -= 1;
                                  break;
                                case '20':
                                  sum -= 20;
                                  middle -= 1;
                                  break;
                                case '50':
                                  sum -= 50;
                                  expansive -= 1;
                                  break;
                            }

                        $('.checked-result').text('¥'+sum)
                    }

                    //data element init
                    var number = $('.checked-place').children().length;

                    //data element set 
                    numberTicket.val(number);
                    sumTicket.val(sum);
                    cheapTicket.val(cheap);
                    middleTicket.val(middle);
                    expansiveTicket.val(expansive );


                    //data element init
                    var chooseSits = '';
                    $('.choosen-place').each( function () {
                        chooseSits += ', '+ $(this).text();
                    });

                    //data element set 
                    sits.val(chooseSits.substr(2));
                    var sid = document.location.href.split('?schedid=')[1].split('#')[0];
                    ss="book3-buy.html?scheid="+sid+"?sits=";
                    $('.sits-area span.choosen-place').each(function(){
                        ss+=$(this).text();
                        ss+=" ";}
                    );
                    $('.booking-pagination__next').attr('href',ss);
                });

				//--- Step for data  ---//
				//Get data from pvevius page
                var url = decodeURIComponent(document.URL);
                var prevDate = url.substr(url.indexOf('?')+1);

                //Serialize, add new data and send to next page
                $('.booking-form').submit( function (e) {
                    e.preventDefault(); 
                    var bookData = $(this).serialize();

                    var fullData = prevDate + '&' + bookData
                    var action, 
                        control = $('.order__control-btn.active').text();

                        if (control == "Purchase"){ action = 'book3-buy.html'; }
                        else if (control == "Reserve"){ action = 'book3-reserve.html'; }

                    $.get( action, fullData, function(data){});
                });

                $('.top-scroll').parent().find('.top-scroll').remove();

        //4. Choosing sits mobile
        //init select box
        $('.sits__sort').selectbox({
        	onChange: function (val, inst) {

        		$(inst.input[0]).children().each(function(item){
        			$(this).removeAttr('selected');
        		})
        		$(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
        	}

        });

        //add new sits line
        $('.add-sits-line').click(function (e){
            e.preventDefault();
            var temp = $('<div class="sits-select"><select name="sorting_item" class="sits__sort sit-row" tabindex="0"><option selected="selected" value="1">A</option><option value="2">B</option><option value="3">C</option><option value="4">D</option><option value="5">E</option><option value="6">F</option><option value="7">G</option> <option value="8">I</option><option value="9">J</option><option value="10">K</option><option value="11">L</option></select><select name="sorting_item" class="sits__sort sit-number" tabindex="1"><option selected="selected" value="1">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option value="14">14</option><option value="15">15</option><option value="16">16</option><option value="17">17</option><option value="18">18</option></select><a href="#" class="btn btn-md btn--warning toogle-sits">Choose sit</a></div>');
            temp.find('.toogle-sits').click(ChoosePlace);
            temp.find('.sits__sort').selectbox({
	        	onChange: function (val, inst) {

	        		$(inst.input[0]).children().each(function(item){
	        			$(this).removeAttr('selected');
	        		})
	        		$(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
	        	}

	        });
            $('.sits-area--mobile-wrap').append(temp);
          

            $(this).blur();

        });

        //choose sits
        $('.toogle-sits').click(ChoosePlace);


        function ChoosePlace (e) {
                e.preventDefault();

                var row = $(this).parent().find('.sit-row option[selected="selected"]').text();
                var number = $(this).parent().find('.sit-number option[selected="selected"]').text();
                var ch_sits = row + number;
                var ticketPrice = 0;
               
                if ( $('.checked-place').find(".choosen-place[data-sit='"+ch_sits+"']").length ) {
                    alert('same place');
                    return 0;
                }

                
                $('.sits-area--mobile .checked-place').prepend('<span class="choosen-place" data-sit="'+ch_sits+'">'+ ch_sits +'</span>');

                ticketPrice = 30;

                switch(ticketPrice)
                        {
                        case 10:
                            sum += 10;
                            break;
                        case 100:
                            sum += 100;
                            break;
                        case 50:
                            sum += 50;
                            break;
                }

                $('.checked-result').text('¥'+sum);

                

                $(this).removeClass('btn--warning').unbind('click',ChoosePlace);
                $(this).addClass('btn--danger').text('remove sit').blur();


             
                $(this).click( function (e){
                    e.preventDefault();

                    var row = $(this).parent().find('.sit-row option[selected="selected"]').text();
                    var numbers = $(this).parent().find('.sit-number option[selected="selected"]').text();
                    var ch_sit = row + number;

                    var activeSit = $('.checked-place').find(".choosen-place[data-sit='"+ch_sits+"']");

                    if ( activeSit.length ) {
                    	activeSit.remove();
                    	$(this).parent().remove();

                        ticketPrice = 30;

                        switch(ticketPrice)
                        {
                                case 10:
                                    sum -= 10;
                                    break;
                                case 50:
                                    sum -= 50;
                                    break;
                                case 100:
                                    sum -= 100;
                                    break;
                        }

                        $('.checked-result').text('¥'+sum);
                    }

                    

            })
        }
        removeBlank();
}
function removeBlank(){
    //去除html用的空白节点
    removeWhiteSpace($(".content_toolbar").get(0));
    function removeWhiteSpace(elem){
        let el = elem || document,//必须是js对象
            cur = el.firstChild,
            temp,
            reg = /\S/;
        while(null != cur){
            temp = cur.nextSibling;
            if( 3 === cur.nodeType && !reg.test(cur.nodeValue) ){//获取的节点是text类型且是空白文本
                el.removeChild(cur);
            }else if( 1 === cur.nodeType ){//如果是元素则递归
                removeWhiteSpace(cur);
            }
            cur = temp;
        }
    }
}

function init_BookingTwo () {
    "use strict";
    var sid = document.location.href.split('?schedid=')[1].split('#')[0];
    axios.get('http://localhost:8081/purchase/seatInfo',{params:{schedualId:sid}})
        .then((response)=>{
            if(response.data.data!=null){
                console.log(response);
                var seat_info=response.data.data.seat_info;
                
                var price=response.data.data.schedual_info.price;
                var slist={seat_info:seat_info,pricee:price};
                console.log(slist);
                $("#demo1").tmpl(slist).appendTo(".sits");  
                init_BookingTwo_2();
            }
            
        })
        .catch(function (error) {
            console.log(error);
        });     

	
}   

function init_BookingThree(){
    var sid = document.location.href.split('?scheid=')[1].split('?')[0];
    var sits= document.location.href.split('?sits=')[1].split('%20');
    var slist={};
    $('.booking-pagination__prev').attr('href','book2.html?schedid='+sid);
    axios.get('http://localhost:8081/purchase/seatInfo',{params:{schedualId:sid}})
    .then((response)=>{
        if(response.data.data!=null){
            var len=sits.length;
            var cinema=response.data.data.schedual_info.cinema.cinemaName;
            var movie=response.data.data.schedual_info.movie.name;
            var price=response.data.data.schedual_info.price;
            var time=response.data.data.schedual_info.startDate.split('T')[1].split('.')[0];
            var date=response.data.data.schedual_info.startDate.split('T')[0];
            var hall=response.data.data.schedual_info.hall.hallName;
            var total=len*price;
            slist={price:price,num:len,sits:sits,total:total,cinema:cinema,movie:movie,time:time,date:date,hall:hall};
            console.log(slist);
            $("#demo1").tmpl(slist).appendTo(".checkout-wrapper");  
            $('.order').click(function(){
                var tlist=[];
                for(var i = 0; i < len; i++) {
                    var r =sits[i].substr(0,1).charCodeAt()-65;
                    var c=parseInt(sits[i].substr(1))-1;
                    var t=[];
                    t.push(r);
                    t.push(c);
                    tlist.push(t);
                }
                axios.post('http://localhost:8081/purchase/checkOut',{
                    "row_col":tlist,
                    "schedualId": sid
                    })
                    .then((response)=> {
                        //console.log(response);
                        var tickets_info=response.data.data.ticket_infos;
                        var tlen=tickets_info.length;
                        var tids=[];
                        for(var i = 0; i < tlen; i++) {
                            tids.push(tickets_info[i].id);
                        }
                        axios.post('http://localhost:8081/purchase/buy',{
                            "price": total,
                            "schedualId": sid,
                            "ticket_ids": tids,
                            "userId": 1
                            })
                            .then((response)=> {

        
                                localStorage.setItem("ning",JSON.stringify(slist));//设置数据
                                window.location.href="book-final.html";
                
                
                            })
                            .catch(function (error) {
                                console.log(error);
                            });
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            });
        }
        
    })
    .catch(function (error) {
        console.log(error);
    });     
}

function init_BookingFour(){
    $(function(){


        var a=JSON.parse(localStorage.getItem("ning"));//提取数据
        $("#demo1").tmpl(a).appendTo(".ticket");  
    }); 


}
function init_CinemaList_2 (){
    $('.search__button').click(function (ev) {
        ev.preventDefault();
        var search=$('#search-form input').val();
        if(search==""){
            alert("请输入要查找的内容！");
        }else{
            axios.post('http://localhost:8081/cinema/cinemaFliter?cinema_name='+search)
            .then((response)=> {
                console.log(response);
                if(response.data.data!=null){
                    var mid=response.data.data.movie_infos[0].movie_id;
                    window.location.href="single-cinema.html?cid="+mid;
                }else{
                    alert("抱歉！没有与<"+search+">相关的影院。");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
        }
    });
	//1. Dropdowns
				//select
                $(".select__sort").selectbox({
                    onChange: function (val, inst) {

                        $(inst.input[0]).children().each(function(item){
                            $(this).removeAttr('selected');
                        })
                        $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
                    }

                });

    //2. Sorting buy category
                // sorting function
                $('.tags__item').click(function (e) {
                    //prevent the default behaviour of the link
                    e.preventDefault();

                        $('.tags__item').removeClass('item-active');
                        $(this).addClass('item-active');

                        var filter = $(this).attr('data-filter');

                        //show all the list items(this is needed to get the hidden ones shown)
                        $(".cinema-item").show();
                        //hide advertazing and pagination block
                        $('.adv-place').show();
                        $('.pagination').show();
                            
                        /*using the :not attribute and the filter class in it we are selecting
                            only the list items that don't have that class and hide them '*/
                        if ( filter.toLowerCase()!=='all'){
                            $('.cinema-item:not(.' + filter + ')').hide();
                            //show advertazing and pagination block only on filter (all)
                            $('.pagination').hide();
                            $('.adv-place').hide();
                            // fix grid position
                            $('.row').css('clear','none');
                        }
                });
}
function init_CinemaList () {
    "use strict";
    var movie_list_page_num=0;
    var movie_list_page_size=12;
    axios.get('http://localhost:8081/cinema/getList',{params:{pageNumber:movie_list_page_num,pageSize:movie_list_page_size}})
    .then((response)=>{
        if(response.data.data!=null){
            console.log(response);
            var cinemas=response.data.data.cinemas;
            var k={cinemas:cinemas};
            $("#demo1").tmpl(k).appendTo(".row");  
            init_CinemaList_2 ();
        }       
    })
    .catch(function (error) {
        console.log(error);
    });     
}

function init_Contact () {
    "use strict";

	//1. Fullscreen map init
				//Init map
                    var mapOptions = {
                        scaleControl: true,
                        center: new google.maps.LatLng(51.509708, -0.130539),
                        zoom: 15,
                        navigationControl: false,
                        streetViewControl: false,
                        mapTypeControl: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(document.getElementById('location-map'),mapOptions);
                    var marker = new google.maps.Marker({
                        map: map,
                        position: map.getCenter() 
                    });

                    //Custome map style
                    var map_style = [{stylers:[{saturation:-100},{gamma:3}]},{elementType:"labels.text.stroke",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"road",elementType:"geometry",stylers:[{visibility:"simplified"}]},{featureType:"water",stylers:[{visibility:"on"},{saturation:0},{gamma:2},{hue:"#aaaaaa"}]},{featureType:"administrative.neighborhood",elementType:"labels.text.fill",stylers:[{visibility:"off"}]},{featureType:"road.local",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"transit.station",elementType:"labels.icon",stylers:[{visibility:"off"}]}]

                    //Then we use this data to create the styles.
                    var styled_map = new google.maps.StyledMapType(map_style, {name: "Cusmome style"});

                    map.mapTypes.set('map_styles', styled_map);
                    map.setMapTypeId('map_styles');


                    //=====================================

                    // Maker

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextA = document.createElement("div");
                    boxTextA.innerHTML = '<span class="pop_up_box_text">Leicester Sq, London, WC2H 7LP</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsA = {
                     content: boxTextA
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxA = new InfoBox(infoboxOptionsA);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(marker, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxA.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(marker.getPosition());
                    });
}

function init_Gallery () {
    "use strict";
	//1. Pop up fuction for gallery elements

				//pop up for photo (object - images)
                $('.gallery-item--photo').magnificPopup({
                    type: 'image',
                    closeOnContentClick: true,
                    mainClass: 'mfp-fade',
                    image: {
                        verticalFit: true
                    },
                    gallery: {
                        enabled: true,
                        navigateByImgClick: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    }
                    
                });

                //pop up for photo (object - title link)
                $('.gallery-item--photo-link').magnificPopup({
                    type: 'image',
                    closeOnContentClick: true,
                    mainClass: 'mfp-fade',
                    image: {
                        verticalFit: true
                    },
                    gallery: {
                        enabled: true,
                        navigateByImgClick: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    }
                    
                });

                //pop up for video (object - images)
                 $('.gallery-item--video').magnificPopup({
                    disableOn: 700,
                    type: 'iframe',
                    mainClass: 'mfp-fade',
                    removalDelay: 160,
                    preloader: false,

                    fixedContentPos: false,
                    gallery: {
                        enabled: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    }
                });

                //pop up for video (object - title link)
                 $('.gallery-item--video-link').magnificPopup({
                    disableOn: 700,
                    type: 'iframe',
                    mainClass: 'mfp-fade',
                    removalDelay: 160,
                    preloader: false,

                    fixedContentPos: false,
                    gallery: {
                        enabled: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    }
                });
}
var page_num=0;
var page_size=6;
function init_MovieList () {
    "use strict";
    f2();
}
  //对数组进行排序
  function compare_up(property) {
    return (firstobj, secondobj) => {
      const firstValue = firstobj[property];
      const secondValue = secondobj[property];
      return firstValue - secondValue ; //升序
    };
  }
  //对数组进行排序
  function compare_down(property) {
    return (firstobj, secondobj) => {
      const firstValue = firstobj[property];
      const secondValue = secondobj[property];
      return secondValue - firstValue; //降序
    };
  }
function f2(){
    var params={};
    var state= $("[selected='selected']").eq(0).val();
    if(state==1){
        state="on";
    }else if(state==2){
        state="pre";
    }else{
        state="off";
    }
    var cinema= $("[selected='selected']").eq(1).text();
    var tags=[]
    tags.push($("[selected='selected']").eq(2).text());
    var sdate=$(".datepicker input").attr('value');
    sdate=sdate.split('/');
    var date=[];
    date[0]=sdate[2];
    date[1]=sdate[0];
    date[2]=sdate[1];
    date=date.join('-');

    var search=$('#search-form input').val();
    
    if(search==""){
        if(tags[0]!="任意"){
            if(cinema=="任意"){
                params={
                    'state':state,
                    'tags':tags,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'date':date,
                };   
            }else{
                params={
                    'state':state,
                    'tags':tags,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'cinema_name':cinema,
                    'date':date
                };   
            }
        }else{
            if(cinema=="任意"){
                params={
                    'state':state,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'date':date
                };   
            }else{
                params={
                    'state':state,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'cinema_name':cinema,
                    'date':date
                };   
            }
        }
    }else{
        if(tags[0]!="任意"){
            if(cinema=="任意"){
                params={
                    'state':state,
                    'tags':tags,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'date':date,
                    'key_string':search
                };   
            }else{
                params={
                    'state':state,
                    'tags':tags,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'cinema_name':cinema,
                    'date':date,
                    'key_string':search
                };   
            }
        }else{
            if(cinema=="任意"){
                params={
                    'state':state,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'date':date,
                    'key_string':search
                };   
            }else{
                params={
                    'state':state,
                    'pageNumber':page_num,
                    'pageSize':page_size,
                    'cinema_name':cinema,
                    'date':date,
                    'key_string':search
                };   
            }
        }
    }
    axios.post('http://localhost:8081/moive/Fliter/details',params)
        .then((response)=> {
            console.log(response);
            if(response.data.data!=null){
                var mlist=response.data.data.cinemas_infos;
                var mmlist=[];
                var j,k,i;
                var len,tlen,slen;
                var m,mid,taglist,tags,stafflist,director,actors;
                for(j = 0,len=mlist.length; j < len; j++) {
                    m=mlist[j];
                    mid=m.id;
                    taglist=m.tagList;
                    tags="";
                    for(i=0,tlen=taglist.length;i<tlen;i++){
                        tags+=taglist[i].tagName;
                        if(i+1<tlen){
                            tags+="|";
                        }
                    }
                    stafflist=m.staffList;
                    director="";
                    actors="";
                    for(k=0,slen=stafflist.length;k<slen;k++){
                        if(stafflist[k].role=="director"){
                            director+=stafflist[k].staffName;
                            director+=" ";
                        }else{
                            actors+=stafflist[k].staffName;
                            actors+=" ";
                        }
                    }                  
                    var temp={src:'movie-page-full.html?id='+mid,imgsrc:'images/movie/'+mid+'/post.jpg',title:m.name,time:m.duration,option:tags,rating:m.score,comments_num:m.comments_num,pic_num:m.figureList.length,country:m.country,director:director,actors:actors,release_time:m.releaseTime,id:mid};
                    //console.log(temp);
                    mmlist.push(temp);
                }
                var f=$(".item-active").text();
                if(f=="默认"){
                    mmlist.sort(compare_up("id"));
                }else if(f=="上映日期"){
                    mmlist.sort(compare_down("release_time"));
                }else if(f=="热度"){
                    mmlist.sort(compare_down("comments_num"));
                }else{
                    mmlist.sort(compare_down("rating"));
                }
            }
            $("#kk").empty();
            $("#demo").tmpl(mmlist).appendTo('#kk');  
            f1();

        })
        .catch(function (error) {
            console.log(error);
        });
}

function f1(){
           	//1. Dropdown init 
                //select
                $(".select__sort").selectbox({
                    onChange: function (val, inst) {

                        $(inst.input[0]).children().each(function(item){
                            $(this).removeAttr('selected');
                        })
                        $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
                        f2();
                    }

                });

    
    //2. Datepicker init
                $( ".datepicker__input" ).datepicker({
                  showOtherMonths: true,
                  selectOtherMonths: true,
                  showAnim:"fade",
                  onSelect: function(selectedDate){
                    f2();
                }
                });

                $(document).click(function(e) { 
                    var ele = $(e.target); 
                    if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
                       $(".datepicker__input").datepicker("hide");
                     }
                });

                //3. Rating scrore init
                //Rating star
                // $('.score').raty({
                //     width:130, 
                //     number:5,
                //     score: 0,
                //     path: 'images/rate/',
                //     starOff : 'star-off.svg',
                //     starOn  : 'star-on.svg' 
                // });

                $('.score').each(function(e){
                    
                    var rate=parseInt($(this).parent().children().eq(2).text()/2);
                    $(this).raty({
                        width:130, 
                        number:5,
                        score: rate,
                        path: 'images/rate/',
                        starOff : 'star-off.svg',
                        starOn  : 'star-on.svg' 
                    });
                    // console.log(rate>9);
                    // if(rate<2){  
                    //     $(this).raty({
                    //         width:130, 
                    //         number:5,
                    //         score: 1,
                    //         path: 'images/rate/',
                    //         starOff : 'star-off.svg',
                    //         starOn  : 'star-on.svg' 
                    //     });
                    // }else if(2<rate<4){
                    //     $(this).raty({
                    //         width:130, 
                    //         number:5,
                    //         score: 2,
                    //         path: 'images/rate/',
                    //         starOff : 'star-off.svg',
                    //         starOn  : 'star-on.svg' 
                    //     });
                    // }else if(4<rate<6){
                    //     $(this).raty({
                    //         width:130, 
                    //         number:5,
                    //         score: 3,
                    //         path: 'images/rate/',
                    //         starOff : 'star-off.svg',
                    //         starOn  : 'star-on.svg' 
                    //     });
                    // }else if(6<rate<8){
                    //     $(this).raty({
                    //         width:130, 
                    //         number:5,
                    //         score: 4,
                    //         path: 'images/rate/',
                    //         starOff : 'star-off.svg',
                    //         starOn  : 'star-on.svg' 
                    //     });
                    // }else{
                    //     $(this).raty({
                    //         width:130, 
                    //         number:5,
                    //         score: 5,
                    //         path: 'images/rate/',
                    //         starOff : 'star-off.svg',
                    //         starOn  : 'star-on.svg' 
                    //     });
                    // }
                });

                //4. Sorting by category
    			// sorting function
                $('.tags__item').click(function (e) {
                    //prevent the default behaviour of the link
                    e.preventDefault();

                        //active sorted item
                        $('.tags__item').removeClass('item-active');
                        $(this).addClass('item-active');
                        page_num=0;
                        f2();

                        // var filter = $(this).attr('data-filter');

                        // //show all the list items(this is needed to get the hidden ones shown)
                        // $(".movie--preview").show();
                       
                        // $('.pagination').show();
                            
                        // /*using the :not attribute and the filter class in it we are selecting
                        //     only the list items that don't have that class and hide them '*/
                        // if ( filter.toLowerCase()!=='all'){
                        //     $('.movie--preview:not(.' + filter + ')').hide();
                        //     //Show pagination on filter = all;
                        //     $('.pagination').hide();
                        // }
                });

	//5. Toggle function for additional content
                //toggle timetable show
                $('.movie__show-btn').click(function (ev) {
                    ev.preventDefault();
                var mid=$(this).attr('name');
                var date=$('#datepicker').attr('value');

                axios.get('http://localhost:8081/cinema/movieScheduals',{params:{movie_id:mid,date:date}})
                    .then((response)=>{
                        if(response.data.data!=null){
                            console.log(response);
                            var tlist=response.data.data.cinema_infos;
                            var p=[];
                            for(var t in tlist){
                                var items=[];
                                for(var s in tlist[t].sched_infos){
                                    var i=[];
                                    i.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
                                    i.push(tlist[t].sched_infos[s].sched_id);
                                    items.push(i);
                                }
                                var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
                                console.log(k);
                                p.push(k);
                            }
                            console.log(p);
                            $("#k"+mid).empty();
                            $("#demo2").tmpl(p).appendTo("#k"+mid);  
                            //f1();
                        }
                        
                    })
                    .catch(function (error) {
                        console.log(error);
                    });      

                    $(this).parents('.movie--preview').find('.time-select').slideToggle(500);
                });

                $('.time-select__item').click(function (){
                    $('.time-select__item').removeClass('active');
                    $(this).addClass('active');
                });

                $('.search__button').click(function (ev) {
                    ev.preventDefault();
                    f2();
                });

                $('.pagination__prev').click(function (ev) {
                    ev.preventDefault();
                    if(page_num!=0){
                        page_num-=1;
                    f2();
                    }
                });

                $('.pagination__next').click(function (ev) {
                    ev.preventDefault();
                    page_num+=1;
                    f2();
                });
                $('.watchlist').click(function (ev) {
                    ev.preventDefault();
                    var mid= parseInt($(this).parent().parent().children().eq(0).attr('href').split('id=')[1]);
                    axios.post('http://localhost:8081/collect/add?movieId='+mid+'&userId=1')
                    .then((response)=>{
                        if(response.data.data!=null){
                            alert(response.data.data.msg);
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });      
                });
}
var comments_page_num=0;
var comments_page_size=4;
function init_MoviePage () {
    "use strict";
    var r1,r2,r3;
    r1=false;
    r2=false;
    r3=false;
    var mid = document.location.href.split('?id=')[1].split('#')[0];
    axios.get('http://localhost:8081/movie/movieDetails',{params:{movie_Id:mid}})
                    .then((response)=>{
                        var movie_info=response.data.data.movie_info;
                        var staff_info=response.data.data.staff_info;
                        
                        var stafflist=staff_info;
                        var director="";
                        var actors="";
                        for(var s in stafflist){
                            if(stafflist[s].role=="director"){
                                director+=stafflist[s].name;
                                director+=" ";
                            }else{
                                actors+=stafflist[s].name;
                                actors+=" ";
                            }
                        }   
                        movie_info.director=director;
                        movie_info.actors=actors;  
                        movie_info.releaseTime=movie_info.releaseTime.split('T')[0];
                        movie_info.pic_num=movie_info.figureList.length;
                        var fList=[];
                        for(var f in movie_info.figureList){
                            fList.push(movie_info.figureList[f].imageurl);
                        }
                        movie_info.fList=fList;
                        $("#demo1").tmpl(movie_info).appendTo('.movie');
                         
                        r1=true;
                        if(r1&&r2&&r3){
                            init_moviepage_2();
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });   
    axios.get('http://localhost:8081/movie/getCommentList',{params:{
        movieId:mid,
        pageNumber:comments_page_num,
        pageSize:comments_page_size
    }})
        .then((response)=>{
            var comments=response.data.data.comments;
            var commentsList=[];
            for(var c in comments){
                var citem={};
                citem.content=comments[c].cotent;
                citem.date=comments[c].date.split('T')[0];
                citem.time=comments[c].date.split('T')[1].split('.')[0];
                citem.userimg=comments[c].user_info.avatar;
                citem.username=comments[c].user_info.userName;
                citem.title=comments[c].title;
                citem.userid=comments[c].user_info.user_id;
                commentsList.push(citem);
            }
            console.log(commentsList);
            $("#demo2").tmpl(commentsList).appendTo('.comment-sets');
            r2=true;
            if(r1&&r2&&r3){
                init_moviepage_2();
            }
        })
        .catch(function (error) {
            console.log(error);
        });
    var sdate=$(".datepicker input").attr('value');
    sdate=sdate.split('/');
    var date=[];
    date[0]=sdate[2];
    date[1]=sdate[0];
    date[2]=sdate[1];
    date=date.join('/');
    axios.get('http://localhost:8081/cinema/movieScheduals',{params:{
        movie_id:mid,
        date:date
        
    }})
    .then((response)=>{
        var tlist=response.data.data.cinema_infos;
        var p=[];
        for(var t in tlist){
            var items=[];
            for(var s in tlist[t].sched_infos){
                var i=[];
                i.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
                i.push(tlist[t].sched_infos[s].sched_id);
                items.push(i);
            }
            var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
            p.push(k);
        }
        console.log(p);
      
        $("#demo3").tmpl(p).appendTo('.time-select');
        r3=true;
        if(r1&&r2&&r3){
            init_moviepage_2();
        }
    })
    .catch(function (error) {
        console.log(error);
    });

  


    
}

function init_moviepage_2(){
    
    $('.search__button').click(function (ev) {
        ev.preventDefault();
        var search=$('#search-form input').val();
        if(search==""){
            alert("请输入要查找的内容！");
        }
        axios.post('http://localhost:8081/moive/Fliter/details',{'pageNumber':0,'pageSize':10,'key_string':search})
        .then((response)=> {
            console.log(response);
            if(response.data.data.movie_infos.length!=0){
                var mid=response.data.data.movie_infos[0].movie_id;
                window.location.href="movie-page-full.html?id="+mid;
            }else{
                alert("抱歉！没有与<"+search+">相关的电影");
            }
        })
        .catch(function (error) {
            console.log(error);
        });
    });
    //1. Rating scrore init
    //Rating star
    $('.score').raty({
        width:130, 
        score: 5,
        path: 'images/rate/',
        starOff : 'star-off.svg',
        starOn  : 'star-on.svg' 
    });

    //2. Swiper slider
    //Media slider
                //init employee sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:4,
                  });

                $('.swiper-slide-active').css({'marginLeft':'-1px'});

                //Media switch
                $('.list--photo').click(function (e){
                    e.preventDefault();

                    var mediaFilter = $(this).attr('data-filter');

                    $('.swiper-slide').hide();
                    $('.' + mediaFilter).show();

                    $('.swiper-wrapper').css('transform','translate3d(0px, 0px, 0px)')
                    mySwiper.params.slideClass = mediaFilter;
         
                    mySwiper.reInit();
                    $('.swiper-slide-active').css({'marginLeft':'-1px'});
                })

                $('.list--video').click(function (e){
                    e.preventDefault();

                    var mediaFilter = $(this).attr('data-filter');
                    $('.swiper-slide').hide();
                    $('.' + mediaFilter).show();

                    $('.swiper-wrapper').css('transform','translate3d(0px, 0px, 0px)')
                    mySwiper.params.slideClass = mediaFilter;

                    mySwiper.reInit();
                    $('.swiper-slide-active').css({'marginLeft':'-1px'});
                });

                    //media swipe visible slide
                    //Onload detect

                    if ($(window).width() >760 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=3;
                         mySwiper.resizeFix();    
                    
                    } else

                     if ($(window).width() < 480 & $(window).width() > 361){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();    
                    } else
                    if ($(window).width() < 360){
                         mySwiper.params.slidesPerView=1;
                         mySwiper.resizeFix();    
                    }

                    else{
                        mySwiper.params.slidesPerView=4;
                        mySwiper.resizeFix();
                    }

                     if ($('.swiper-container').width() > 900 ){
                        mySwiper.params.slidesPerView=5;
                        mySwiper.resizeFix();
                     }

                  //Resize detect
                $(window).resize(function(){

                     if ($(window).width() >760 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=3;
                          mySwiper.reInit();    
                    
                    } else
                     if ($(window).width() < 480 & $(window).width() > 361){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();   
                    } else 
                    if ($(window).width() < 360){
                         mySwiper.params.slidesPerView=1;
                         mySwiper.reInit();   
                    }

                    else{
                        mySwiper.params.slidesPerView=4;
                        mySwiper.reInit();
                    }


                 });

    //3. Slider item pop up
   				//boolian var
                var toggle = true;

                //pop up video media element
                $('.media-video .movie__media-item').magnificPopup({
                    //disableOn: 700,
                    type: 'iframe',
                    mainClass: 'mfp-fade',
                    removalDelay: 160,
                    preloader: false,

                    fixedContentPos: false,

                    gallery: {
                        enabled: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    },

                    disableOn:function () {
                        return toggle;
                    }
                });

                //pop up photo media element
                $('.media-photo .movie__media-item').magnificPopup({
                    type: 'image',
                    closeOnContentClick: true,
                    mainClass: 'mfp-fade',
                    image: {
                        verticalFit: true
                    },

                    gallery: {
                        enabled: true,
                        navigateByImgClick: true,
                        preload: [0,1] // Will preload 0 - before current, and 1 after the current image
                    },

                    disableOn:function () {
                        return toggle;
                    }
                    
                });

                //detect if was move after click
                $('.movie__media .swiper-slide').on('mousedown', function(e){
                    toggle = true;
                    $(this).on('mousemove', function testMove(){
                          toggle = false;
                    });
                });

    //4. Dropdown init 
				//select
                $("#select-sort").selectbox({
                    onChange: function (val, inst) {

                        $(inst.input[0]).children().each(function(item){
                            $(this).removeAttr('selected');
                        })
                        $(inst.input[0]).find('[value="'+val+'"]').attr('selected','selected');
                    }

                });

    
    //5. Datepicker init
                $( ".datepicker__input" ).datepicker({
                  showOtherMonths: true,
                  selectOtherMonths: true,
                  showAnim:"fade",
                  onSelect: function(selectedDate){
					var mid = document.location.href.split('?id=')[1].split('#')[0];
					var sdate=$(".datepicker input").attr('value');
					sdate=sdate.split('/');
					var date=[];
					date[0]=sdate[2];
					date[1]=sdate[0];
					date[2]=sdate[1];
					date=date.join('/');
					axios.get('http://localhost:8081/cinema/movieScheduals',{params:{
						movie_id:mid,
						date:date
						
					}})
					.then((response)=>{
                        if(response.data.data!=null){
                            var tlist=response.data.data.cinema_infos;
                            var p=[];
                            for(var t in tlist){
                                var items=[];
                                for(var s in tlist[t].sched_infos){
                                    items.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
                                    //items.push(tlist[t].sched_infos[s].sched_id);
                                }
                                var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
                                p.push(k);
                            }
                        }
						$(".time-select").empty();
						$("#demo3").tmpl(p).appendTo('.time-select');
						$('.time-select__item').click(function (){
							$('.time-select__item').removeClass('active');
							$(this).addClass('active');
						});
					})
					.catch(function (error) {
						console.log(error);
					});  
                }
                });

                $(document).click(function(e) { 
                    var ele = $(e.target); 
                    if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
                       $(".datepicker__input").datepicker("hide");
                     }
                     
                });
                $('.ui-datepicker-calendar tbody tr td a').click(function(e) { 
                    alert('1');
                    alert($(".datepicker input").attr('value'));
                });
    //6. Reply comment form
    			// button more comments
                $('#hide-comments').hide();

                $('.comment-more').click(function (e) {
                        e.preventDefault();
                        comments_page_num++;
                        var mid = document.location.href.split('?id=')[1].split('#')[0];
                        axios.get('http://localhost:8081/movie/getCommentList',{params:{
                            movieId:mid,
                            pageNumber:comments_page_num,
                            pageSize:comments_page_size
                        }})
                        .then((response)=>{
                            var comments=response.data.data.comments;
                            var commentsList=[];
                            for(var c in comments){
                                var citem={};
                                citem.content=comments[c].cotent;
                                citem.date=comments[c].date.split('T')[0];
                                citem.time=comments[c].date.split('T')[1].split('.')[0];
                                citem.userimg=comments[c].user_info.avatar;
                                citem.username=comments[c].user_info.userName;
                                citem.title=comments[c].title;
                                citem.userid=comments[c].user_info.user_id;
                                commentsList.push(citem);
                            }
                            console.log(commentsList);
                            $("#demo2").tmpl(commentsList).appendTo('.comment-sets');
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                        //$('#hide-comments').slideDown(400);
                        //$(this).hide();
                })

                  //reply comment function
                  $('.comment__reply').click( function (e) {
                        e.preventDefault();

                        $('.comment').find('.comment-form').remove();
                        $(this).parent().append("<form id='comment-form' class='comment-form' method='post'>\
                            <textarea class='comment-form__text' placeholder='Add you comment here'></textarea>\
                            <label class='comment-form__info'>250 characters left</label>\
                            <button type='submit' class='btn btn-md btn--danger comment-form__btn'>add comment</button>\
                        </form>");
                  });

    //7. Timetable active element
    			$('.time-select__item').click(function (){
                    $('.time-select__item').removeClass('active');
                    $(this).addClass('active');
                });
                $('.ui-state-default').click(function (){
                    alert($(".datepicker input").attr('value'));
                });
                 

    //8. Toggle between cinemas timetable and map with location
    			//change map - ticket list
                $('#map-switch').click(function(ev){
                    ev.preventDefault();

                    $('.time-select').slideToggle(500);
                    $('.map').slideToggle(500);

                    $('.show-map').toggle();
                    $('.show-time').toggle();
                    $(this).blur();
                });

                $(window).load(function () {
                    $('.map').addClass('hide-map');
                })

   	//9. Init map with several markers on.
   					//Map start init
                    var mapOptions = {
                        scaleControl: true,
                        center: new google.maps.LatLng(51.508798, -0.131687),
                        zoom: 15,
                        navigationControl: false,
                        streetViewControl: false,
                        mapTypeControl: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(document.getElementById('cimenas-map'),mapOptions);
                    var marker = new google.maps.Marker({
                        map: map,
                        position: map.getCenter()
                    });

                    var markerB = new google.maps.Marker({
                        map: map,
                        position: new google.maps.LatLng(51.510838, -0.130400)
                    });

                    var markerC = new google.maps.Marker({
                        map: map,
                        position: new google.maps.LatLng(51.512615, -0.130607)
                    });

                    var markerD = new google.maps.Marker({
                        map: map,
                        position: new google.maps.LatLng(51.509859, -0.130213)
                    });

                    var markerE = new google.maps.Marker({
                        map: map,
                        position: new google.maps.LatLng(51.509194, -0.130091)
                    });


                    //Custome map style
                    var map_style = [{stylers:[{saturation:-100},{gamma:3}]},{elementType:"labels.text.stroke",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"road",elementType:"geometry",stylers:[{visibility:"simplified"}]},{featureType:"water",stylers:[{visibility:"on"},{saturation:0},{gamma:2},{hue:"#aaaaaa"}]},{featureType:"administrative.neighborhood",elementType:"labels.text.fill",stylers:[{visibility:"off"}]},{featureType:"road.local",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"transit.station",elementType:"labels.icon",stylers:[{visibility:"off"}]}]

                    //Then we use this data to create the styles.
                    var styled_map = new google.maps.StyledMapType(map_style, {name: "Cusmome style"});

                    map.mapTypes.set('map_styles', styled_map);
                    map.setMapTypeId('map_styles');

                    //=====================================

                    // Maker A

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextA = document.createElement("div");
                    boxTextA.innerHTML = '<span class="pop_up_box_text">Cineworld, 63-65 Haymarket, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsA = {
                     content: boxTextA
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxA = new InfoBox(infoboxOptionsA);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(marker, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxA.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(marker.getPosition());
                    });



                    //=====================================

                    // Maker B

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextB = document.createElement("div");
                    boxTextB.innerHTML = '<span class="pop_up_box_text">Empire Cinemas, 5-6 Leicester Square, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsB = {
                     content: boxTextB
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxB = new InfoBox(infoboxOptionsB);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(markerB, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxB.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(markerB.getPosition());
                    });

                    //=====================================

                    // Maker C

                    //=====================================


                    //Creates the information to go in the pop-up info box.
                    var boxTextC = document.createElement("div");
                    boxTextC.innerHTML = '<span class="pop_up_box_text">Curzon Soho, 99 Shaftesbury Ave , London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsC = {
                     content: boxTextC
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxC = new InfoBox(infoboxOptionsC);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(markerC, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxC.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(markerC.getPosition());
                    });

                    //=====================================

                    // Maker D

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextD = document.createElement("div");
                    boxTextD.innerHTML = '<span class="pop_up_box_text">Odeon Cinema West End, Leicester Square, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsD = {
                     content: boxTextD
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxD = new InfoBox(infoboxOptionsD);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(markerD, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxD.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(markerD.getPosition());
                    });



                    //=====================================

                    // Maker E

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextE = document.createElement("div");
                    boxTextE.innerHTML = '<span class="pop_up_box_text">Picturehouse Cinemas Ltd, Orange Street, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsE = {
                     content: boxTextE
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxE = new InfoBox(infoboxOptionsE);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(markerE, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxE.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(markerE.getPosition());
                    });
    
    //10. Scroll down navigation function
    //scroll down
    $('.comment-link').click(function (ev) {
        ev.preventDefault();
        $('html, body').stop().animate({'scrollTop': $('.comment-wrapper').offset().top-90}, 900, 'swing');
    });

    $('.datepicker').datepicker({
        selectDay: function(dateText, inst) {
        alert("122");
        } });

    // var mid = document.location.href.split('?id=')[1].split('#')[0];
	// 				var sdate=$(".datepicker input").attr('value');
	// 				sdate=sdate.split('/');
	// 				var date=[];
	// 				date[0]=sdate[2];
	// 				date[1]=sdate[0];
	// 				date[2]=sdate[1];
	// 				date=date.join('/');
	// 				axios.get('http://localhost:8081/cinema/movieScheduals',{params:{
	// 					movie_id:mid,
	// 					date:date
						
	// 				}})
	// 				.then((response)=>{
	// 					var tlist=response.data.data.cinema_infos;
	// 					var p=[];
	// 					for(var t in tlist){
	// 						var items=[];
	// 						for(var s in tlist[t].sched_infos){
	// 							items.push(tlist[t].sched_infos[s].start_date.split('T')[1].substring(0,5));
	// 							//items.push(tlist[t].sched_infos[s].sched_id);
	// 						}
	// 						var k={cinema_name:tlist[t].cinema_name,sched_infos:items};
	// 						p.push(k);
	// 					}
	// 					$(".time-select").empty();
	// 					$("#demo3").tmpl(p).appendTo('.time-select');
	// 					$('.time-select__item').click(function (){
	// 						$('.time-select__item').removeClass('active');
	// 						$(this).addClass('active');
	// 					});
	// 				})
	// 				.catch(function (error) {
	// 					console.log(error);
	// 				});
}

function init_MoviePageFull () {
    "use strict";

            //init employee sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:5,
                  });

                $('.swiper-slide-active').css({'marginLeft':'-1px'});

                //Media switch
                $('.list--photo').click(function (e){
                    e.preventDefault();

                    var mediaFilter = $(this).attr('data-filter');

                    $('.swiper-slide').hide();
                    $('.' + mediaFilter).show();

                    $('.swiper-wrapper').css('transform','translate3d(0px, 0px, 0px)')
                    mySwiper.params.slideClass = mediaFilter;
         
                    mySwiper.reInit();
                    $('.swiper-slide-active').css({'marginLeft':'-1px'});
                })

                $('.list--video').click(function (e){
                    e.preventDefault();

                    var mediaFilter = $(this).attr('data-filter');
                    $('.swiper-slide').hide();
                    $('.' + mediaFilter).show();

                    $('.swiper-wrapper').css('transform','translate3d(0px, 0px, 0px)')
                    mySwiper.params.slideClass = mediaFilter;

                    mySwiper.reInit();
                    $('.swiper-slide-active').css({'marginLeft':'-1px'});
                });
                    //media swipe visible slide
                    //Onload detect

                    if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=3;
                         mySwiper.resizeFix();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=3;
                         mySwiper.resizeFix();    
                    
                    } else
                     if ($(window).width() < 480 & $(window).width() > 361){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();    
                    } else
                    if ($(window).width() < 360){
                         mySwiper.params.slidesPerView=1;
                         mySwiper.resizeFix();    
                    }

                    else{
                        mySwiper.params.slidesPerView=5;
                        mySwiper.resizeFix();
                    }

                     if ($('.swiper-container').width() > 900 ){
                        mySwiper.params.slidesPerView=5;
                        mySwiper.resizeFix();
                     }

                  //Resize detect
                $(window).resize(function(){
                  if ($(window).width() > 993 & $('.swiper-container').width() > 900 ){
                        mySwiper.params.slidesPerView=5;
                        mySwiper.reInit(); 
                     }

                     if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=3;
                         mySwiper.reInit();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=3;
                          mySwiper.reInit();    
                    
                    } else
                     if ($(window).width() < 480 & $(window).width() > 361){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();   
                    } else 
                    if ($(window).width() < 360){
                         mySwiper.params.slidesPerView=1;
                         mySwiper.reInit();   
                    }

                    else{
                        mySwiper.params.slidesPerView=5;
                        mySwiper.reInit();
                    }


                 });
}

function init_Rates () {
    "use strict";

	//1. Rating fucntion
				//Rating star
                $('.score').raty({
                    width:130, 
                    score: 0,
                    path: 'images/rate/',
                    starOff : 'star-off.svg',
                    starOn  : 'star-on.svg' 
                });

                //After rate callback
                $('.score').click(function () {
                    $(this).children().hide();

                    $(this).html('<span class="rates__done">Thanks for your vote!<span>')
                })
}

function init_Cinema_2(){
    //1. Swiper slider
				//init cinema sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:8,
                    loop:true,
                  });

                $('.swiper-slide-active').css({'marginLeft':'-1px'});
                //media swipe visible slide

                //onload detect
                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.resizeFix();         
                    }
                    else
                    if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.resizeFix();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                         mySwiper.resizeFix();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();    
                    }

                    else{
                        mySwiper.params.slidesPerView=8;
                        mySwiper.resizeFix();
                    }

				//resize detect                   
                $(window).resize(function(){
                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.reInit();          
                    }
                    else
                     if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.reInit();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                          mySwiper.reInit();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();   
                    }

                    else{
                        mySwiper.params.slidesPerView=8;
                        mySwiper.reInit();
                    }
                 });

	//2. Datepicker
				//datepicker
                $( ".datepicker__input" ).datepicker({
                  showOtherMonths: true,
                  selectOtherMonths: true,
                  showAnim:"fade"
                });

                $(document).click(function(e) { 
                    var ele = $(e.target); 
                    if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
                       $(".datepicker__input").datepicker("hide");

                     }

                });

    //3. Comment area control
    			// button more comments
                  $('#hide-comments').hide();

                  $('.comment-more').click(function (e) {
                        e.preventDefault();
                        $('#hide-comments').slideDown(400);
                        $(this).hide();
                  })

                  //reply comment function

                  $('.comment__reply').click( function (e) {
                        e.preventDefault();

                        $('.comment').find('.comment-form').remove();


                        $(this).parent().append("<form id='comment-form' class='comment-form' method='post'>\
                            <textarea class='comment-form__text' placeholder='Add you comment here'></textarea>\
                            <label class='comment-form__info'>250 characters left</label>\
                            <button type='submit' class='btn btn-md btn--danger comment-form__btn'>add comment</button>\
                        </form>");
                  });

    //4. Init map
    				var mapOptions = {
                        scaleControl: true,
                        center: new google.maps.LatLng(51.508798, -0.131687),
                        zoom: 16,
                        navigationControl: false,
                        streetViewControl: false,
                        mapTypeControl: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(document.getElementById('cinema-map'),mapOptions);
                    var marker = new google.maps.Marker({
                        map: map,
                        position: map.getCenter()
                    });

                    //Custome map style
                    var map_style = [{stylers:[{saturation:-100},{gamma:3}]},{elementType:"labels.text.stroke",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"road",elementType:"geometry",stylers:[{visibility:"simplified"}]},{featureType:"water",stylers:[{visibility:"on"},{saturation:0},{gamma:2},{hue:"#aaaaaa"}]},{featureType:"administrative.neighborhood",elementType:"labels.text.fill",stylers:[{visibility:"off"}]},{featureType:"road.local",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"transit.station",elementType:"labels.icon",stylers:[{visibility:"off"}]}]

                    //Then we use this data to create the styles.
                    var styled_map = new google.maps.StyledMapType(map_style, {name: "Cusmome style"});

                    map.mapTypes.set('map_styles', styled_map);
                    map.setMapTypeId('map_styles');


                    //=====================================

                    // Maker

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextA = document.createElement("div");
                    boxTextA.innerHTML = '<span class="pop_up_box_text">Cineworld, 63-65 Haymarket, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsA = {
                     content: boxTextA
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxA = new InfoBox(infoboxOptionsA);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(marker, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxA.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(marker.getPosition());
                    });
                    removeBlank();
}
function init_Cinema () {
    "use strict";
    var cid=document.location.href.split('?cid=')[1].split('#')[0];
    axios.get('http://localhost:8081/cinema/getScheduals',{params:{cinema_id:cid}})
    .then((response)=>{
        if(response.data.data!=null){
            console.log(response.data.data);
            var k={cinema:response.data.data[0].cinema};
            $("#demo1").tmpl(k).appendTo(".cinema--full");  
            init_Cinema_2();
        }       
    })
    .catch(function (error) {
        console.log(error);
    });     
}

function init_User_2(){
    //1. Swiper slider
				//init cinema sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:8,
                    loop:true,
                  });

                $('.swiper-slide-active').css({'marginLeft':'-1px'});
                //media swipe visible slide

                //onload detect
                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.resizeFix();         
                    }
                    else
                    if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.resizeFix();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                         mySwiper.resizeFix();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.resizeFix();    
                    }

                    else{
                        mySwiper.params.slidesPerView=8;
                        mySwiper.resizeFix();
                    }

				//resize detect                   
                $(window).resize(function(){
                    if ($(window).width() >993 & $(window).width() <  1199  ){
                         mySwiper.params.slidesPerView=6;
                         mySwiper.reInit();          
                    }
                    else
                     if ($(window).width() >768 & $(window).width() <  992  ){
                         mySwiper.params.slidesPerView=5;
                         mySwiper.reInit();         
                    }

                    else
                    if ($(window).width() < 767 & $(window).width() > 481){
                         mySwiper.params.slidesPerView=4;
                          mySwiper.reInit();    
                    
                    } else
                     if ($(window).width() < 480){
                         mySwiper.params.slidesPerView=2;
                         mySwiper.reInit();   
                    }

                    else{
                        mySwiper.params.slidesPerView=8;
                        mySwiper.reInit();
                    }
                 });

	//2. Datepicker
				//datepicker
                $( ".datepicker__input" ).datepicker({
                  showOtherMonths: true,
                  selectOtherMonths: true,
                  showAnim:"fade"
                });

                $(document).click(function(e) { 
                    var ele = $(e.target); 
                    if (!ele.hasClass("datepicker__input") && !ele.hasClass("ui-datepicker") && !ele.hasClass("ui-icon") && !$(ele).parent().parents(".ui-datepicker").length){
                       $(".datepicker__input").datepicker("hide");

                     }

                });

    //3. Comment area control
    			// button more comments
                  $('#hide-comments').hide();

                  $('.comment-more').click(function (e) {
                        e.preventDefault();
                        $('#hide-comments').slideDown(400);
                        $(this).hide();
                  })

                  //reply comment function

                  $('.comment__reply').click( function (e) {
                        e.preventDefault();

                        $('.comment').find('.comment-form').remove();


                        $(this).parent().append("<form id='comment-form' class='comment-form' method='post'>\
                            <textarea class='comment-form__text' placeholder='Add you comment here'></textarea>\
                            <label class='comment-form__info'>250 characters left</label>\
                            <button type='submit' class='btn btn-md btn--danger comment-form__btn'>add comment</button>\
                        </form>");
                  });

    //4. Init map
    				var mapOptions = {
                        scaleControl: true,
                        center: new google.maps.LatLng(51.508798, -0.131687),
                        zoom: 16,
                        navigationControl: false,
                        streetViewControl: false,
                        mapTypeControl: false,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    var map = new google.maps.Map(document.getElementById('cinema-map'),mapOptions);
                    var marker = new google.maps.Marker({
                        map: map,
                        position: map.getCenter()
                    });

                    //Custome map style
                    var map_style = [{stylers:[{saturation:-100},{gamma:3}]},{elementType:"labels.text.stroke",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.business",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"poi.place_of_worship",elementType:"labels.icon",stylers:[{visibility:"off"}]},{featureType:"road",elementType:"geometry",stylers:[{visibility:"simplified"}]},{featureType:"water",stylers:[{visibility:"on"},{saturation:0},{gamma:2},{hue:"#aaaaaa"}]},{featureType:"administrative.neighborhood",elementType:"labels.text.fill",stylers:[{visibility:"off"}]},{featureType:"road.local",elementType:"labels.text",stylers:[{visibility:"off"}]},{featureType:"transit.station",elementType:"labels.icon",stylers:[{visibility:"off"}]}]

                    //Then we use this data to create the styles.
                    var styled_map = new google.maps.StyledMapType(map_style, {name: "Cusmome style"});

                    map.mapTypes.set('map_styles', styled_map);
                    map.setMapTypeId('map_styles');


                    //=====================================

                    // Maker

                    //=====================================

                    //Creates the information to go in the pop-up info box.
                    var boxTextA = document.createElement("div");
                    boxTextA.innerHTML = '<span class="pop_up_box_text">Cineworld, 63-65 Haymarket, London</span>';

                    //Sets up the configuration options of the pop-up info box.
                    var infoboxOptionsA = {
                     content: boxTextA
                     ,disableAutoPan: false
                     ,maxWidth: 0
                     ,pixelOffset: new google.maps.Size(30, -50)
                     ,zIndex: null
                     ,boxStyle: {
                     background: "#4c4145"
                     ,opacity: 1
                     ,width: "300px"
                     ,color: " #b4b1b2"
                     ,fontSize:"13px"
                     ,padding:'14px 20px 15px'
                     }
                     ,closeBoxMargin: "6px 2px 2px 2px"
                     ,infoBoxClearance: new google.maps.Size(1, 1)
                     ,closeBoxURL: "images/components/close.svg"
                     ,isHidden: false
                     ,pane: "floatPane"
                     ,enableEventPropagation: false
                    };

                    
                    //Creates the pop-up infobox for Glastonbury, adding the configuration options set above.
                    var infoboxA = new InfoBox(infoboxOptionsA);


                    //Add an 'event listener' to the Glastonbury map marker to listen out for when it is clicked.
                    google.maps.event.addListener(marker, "click", function (e) {
                     //Open the Glastonbury info box.
                     infoboxA.open(map, this);
                     //Sets the Glastonbury marker to be the center of the map.
                     map.setCenter(marker.getPosition());
                    });
                    removeBlank();
}
function init_User () {
    var f1,f2,f3=false;
    var uid=document.location.href.split('?uid=')[1];
    axios.get('http://localhost:8081/user/getUserInfo',{params:{userId:uid}})
    .then((response)=>{
        if(response.data.data!=null){
            console.log(response.data.data);
            var k={user_info:response.data.data.user_info};
            $("#demo1").tmpl(k).appendTo(".cinema--full");  
            if(f1&&f2&&f3){
                init_User_2();
            }
        }       
    })
    .catch(function (error) {
        console.log(error);
    });     
    axios.get('http://localhost:8081/user/getCollectedMovies',{params:{userId:uid}})
    .then((response)=>{
        if(response.data.data!=null){
            console.log(response.data.data);
            var k={movies:response.data.data.movies};
            $("#demo2").tmpl(k).appendTo("#movie1");  
            if(f1&&f2&&f3){
                init_User_2();
            }
        }       
    })
    .catch(function (error) {
        console.log(error);
    });  
    axios.get('http://localhost:8081/user/getBuyList',{params:{userId:uid}})
    .then((response)=>{
        if(response.data.data!=null){
            console.log(response.data.data);
            var k={buy_info:response.data.data.buy_info};
            $("#demo3").tmpl(k).appendTo("#comment1");  
            if(f1&&f2&&f3){
                init_User_2();
            }
        }       
    })
    .catch(function (error) {
        console.log(error);
    });  
}

function init_SinglePage () {
    "use strict";

	//1. Swiper slider (with arrow behaviur).
				//init images sliders
                var mySwiper = new Swiper('.swiper-container',{
                    slidesPerView:1,
                    onSlideChangeStart:function change(index){
                        var i = mySwiper.activeIndex;
                        var prev = i-1;
                        var next = i+1;

                        var prevSlide = $('.post__preview .swiper-slide').eq(prev).attr('data-text');
                             $('.arrow-left').find('.slider__info').text(prevSlide);
                        var nextSlide = $('.post__preview .swiper-slide').eq(next).attr('data-text');
                            $('.arrow-right').find('.slider__info').text(nextSlide);

                        //condition first-last slider
                        $('.arrow-left , .arrow-right').removeClass('no-hover');

                        if(i == 0){
                            $('.arrow-left').find('.slider__info').text('');
                            $('.arrow-left').addClass('no-hover');
                        }

                        if(i == last){
                            $('.arrow-right').find('.slider__info').text('');
                            $('.arrow-right').addClass('no-hover');
                        }
                    }
                  });

                //var init and put onload value
                var i = mySwiper.activeIndex;
                var last =mySwiper.slides.length - 1; 
                var prev = i-1;
                var next = i+1;

                var prevSlide = $('.post__preview .swiper-slide').eq(prev).attr('data-text');
                var nextSlide = $('.post__preview .swiper-slide').eq(next).attr('data-text');

                //put onload value for slider navigation
                $('.arrow-left').find('.slider__info').text(prevSlide);
                $('.arrow-right').find('.slider__info').text(nextSlide);

                //condition first-last slider
                if(i == 0){
                    $('.arrow-left').find('.slider__info').text('');
                }

                if(i == last){
                    $('.arrow-right').find('.slider__info').text('');
                }

                //init slider navigation arrow

                  $('.arrow-left').on('click', function(e){
                    e.preventDefault();
                    mySwiper.swipePrev();
                  })
                  $('.arrow-right').on('click', function(e){
                    e.preventDefault();
                    mySwiper.swipeNext();
                  })

	//2. Comment area control
				// button more comments
                  $('#hide-comments').hide();

                  $('.comment-more').click(function (e) {
                        e.preventDefault();
                        $('#hide-comments').slideDown(400);
                        $(this).hide();
                  })

                  //reply comment function

                  $('.comment__reply').click( function (e) {
                        e.preventDefault();

                        $('.comment').find('.comment-form').remove();


                        $(this).parent().append("<form id='comment-form' class='comment-form' method='post'>\
                            <textarea class='comment-form__text' placeholder='Add you comment here'></textarea>\
                            <label class='comment-form__info'>250 characters left</label>\
                            <button type='submit' class='btn btn-md btn--danger comment-form__btn'>add comment</button>\
                        </form>");
                  });
}

function init_Trailer () {
    "use strict";

	//1. Element controls
				//pop up element
                $('.trailer-sample').magnificPopup({
                    disableOn: 700,
                    type: 'iframe',
                    mainClass: 'mfp-fade',
                    removalDelay: 160,
                    preloader: false,

                    fixedContentPos: false
                });

                //show hide content
                $('.trailer-btn').click(function (e) {
                    e.preventDefault();

                    $(this).hide();
                    $(this).parent().addClass('trailer-block--short').find('.hidden-content').slideDown(500);
                })
}
