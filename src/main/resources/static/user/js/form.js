"use strict";

//Plaeholder handler
$(function() {
	
if(!Modernizr.input.placeholder){             //placeholder for old brousers and IE
 
  $('[placeholder]').focus(function() {
   var input = $(this);
   if (input.val() == input.attr('placeholder')) {
    input.val('');
    input.removeClass('placeholder');
   }
  }).blur(function() {
   var input = $(this);
   if (input.val() == '' || input.val() == input.attr('placeholder')) {
    input.addClass('placeholder');
    input.val(input.attr('placeholder'));
   }
  }).blur();
  $('[placeholder]').parents('form').submit(function() {
   $(this).find('[placeholder]').each(function() {
    var input = $(this);
    if (input.val() == input.attr('placeholder')) {
     input.val('');
    }
   })
  });
 }
  
$('#contact-form').submit(function(e) {
      
		e.preventDefault();	
		var error = 0;
		var self = $(this);
		
	    var $name = self.find('[name=user-name]');
	    var $email = self.find('[type=email]');
	    var $message = self.find('[name=user-message]');
		
				
		var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		
  		if(!emailRegex.test($email.val())) {
			createErrTult('Error! Wrong email!', $email)
			error++;	
		}

		if( $name.val().length>1 &&  $name.val()!= $name.attr('placeholder')  ) {
			$name.removeClass('invalid_field');			
		} 
		else {
			createErrTult('Error! Write your name!', $name)
			error++;
		}

		if($message.val().length>2 && $message.val()!= $message.attr('placeholder')) {
			$message.removeClass('invalid_field');
		} 
		else {
			createErrTult('Error! Write message!', $message)
			error++;
		}
		
		
		
		if (error!=0)return;
		self.find('[type=submit]').attr('disabled', 'disabled');

		self.children().fadeOut(300,function(){ $(this).remove() })
		$('<p class="success"><span class="success-huge">Thank you!</span> <br> your message successfully sent</p>').appendTo(self)
		.hide().delay(300).fadeIn();


		var formInput = self.serialize();
		$.post(self.attr('action'),formInput, function(data){}); // end post
}); // end submit

$('.login').submit(function(e) {
      
		e.preventDefault();	
		var error = 0;
		var self = $(this);
		
	    var $username = self.find('[type=email]');
	    var $pass = self.find('[type=password]');
		

		if( $pass.val().length>1 &&  $pass.val()!= $pass.attr('placeholder')  ) {
			$pass.removeClass('invalid_field');			
		} 
		else {
			createErrTult('Error! Wrong password!', $pass)
			error++;
		}

		$.post("http://localhost:8081/user/login",
		{
			username:$username.val(),
			password:$pass.val()
		},
		function(data,status){
			if(data.code==5){
				createErrTult('用户名或密码错误！', $pass);
				error++;
			}else if(data.code==2){
				createErrTult("用户名不存在！", $username);
				error++;
			}
			if (error!=0)return;
			else{
				self.find('[type=submit]').attr('disabled', 'disabled');

				self.children().fadeOut(300,function(){ $(this).remove() })
				$('<p class="login__title">登录<br><span class="login-edition">欢迎使用A.Movie!</span></span></p><p class="success">您已经登录成功！</p>').appendTo(self)
				.hide().delay(300).fadeIn();
			}
		});
		// var formInput = self.serialize();
		// $.post(self.attr('action'),formInput, function(data){}); // end post
}); // end submit
		
$('.signin').submit(function(e) {
      
	e.preventDefault();	
	var error = 0;
	var self = $(this);
	
	var $email = self.find('[type=email]');
	var $pass = self.find('[type=password]');
	var $username=self.find('[type=username]')
	var $phone=self.find('[type=phone]')
	var $gender=self.find('[type=gender]')
	
			
	var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	
	  if(!emailRegex.test($email.val())) {
		createErrTult("错误的邮箱格式！请重新输入！", $email)
		error++;	
	}

	if( $pass.val().length>1 &&  $pass.val()!= $pass.attr('placeholder')  ) {
		$pass.removeClass('invalid_field');			
	} 
	else {
		createErrTult('Error! Wrong password!', $pass)
		error++;
	}

	$.post("http://localhost:8081/user/sign",
	{
		username:$username.val(),
		password:$pass.val(),
		email:$email.val(),
		phone:$phone.val(),
		usersex:$gender.val()
	},function(data,status){
		console.log(data);
		console.log(status);
	});
	
	
	
	if (error!=0)return;
	self.find('[type=submit]').attr('disabled', 'disabled');

	self.children().fadeOut(300,function(){ $(this).remove() })
	$('<p class="login__title">注册<br><span class="login-edition">欢迎使用A.Movie!</span></span></p><p class="success">您已经注册成功！</p>').appendTo(self)
	.hide().delay(300).fadeIn();


	// var formInput = self.serialize();
	// $.post(self.attr('action'),formInput, function(data){}); // end post
}); // end submit	

function createErrTult(text, $elem){
			$elem.focus();
			$('<p />', {
				'class':'inv-em alert alert-danger',
				'html':'<span class="icon-warning"></span>' + text + ' <a class="close" data-dismiss="alert" href="#" aria-hidden="true"></a>',
			})
			.appendTo($elem.addClass('invalid_field').parent()) 
			.insertAfter($elem)
			.delay(4000).animate({'opacity':0},300, function(){ $(this).slideUp(400,function(){ $(this).remove() }) });
	}
});
