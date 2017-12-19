/*
Monthly 2.0.2 by Kevin Thornbloom is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License.
*/

(function($) {
	$.fn.extend({
		monthly: function(options) {
			// These are overridden by options declared in footer
			var defaults = {
				weekStart: 'Sun', //日历第一格开始时间
				mode: '', 
				xmlUrl: '',
				target: '',
				eventList: true,
				maxWidth: false,
				startHidden: false,
				showTrigger: '',
				stylePast: false,
				disablePast: false
			}

			var options = $.extend(defaults, options),
				that = this,
				uniqueId = $(this).attr('id'),    //日历框id
				d = new Date(),					  //当前日期
				currentMonth = d.getMonth() + 1,	//当前月
				currentYear = d.getFullYear(),		//当前年
				currentDay = d.getDate(),
				monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "June", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
				dayNames = ['SUN','MON','TUE','WED','THU','FRI','SAT'];

		if (options.maxWidth != false){
			$('#'+uniqueId).css('maxWidth',options.maxWidth);
		}

		// 添加日历头。周一二。。。。
		if (options.weekStart == 'Sun') {
			$('#' + uniqueId).append('<div class="monthly-day-title-wrap"><div>Sun</div><div>Mon</div><div>Tue</div><div>Wed</div><div>Thu</div><div>Fri</div><div>Sat</div></div><div class="monthly-day-wrap"></div>');
		} else if (options.weekStart == 'Mon') {
			$('#' + uniqueId).append('<div class="monthly-day-title-wrap"><div>Mon</div><div>Tue</div><div>Wed</div><div>Thu</div><div>Fri</div><div>Sat</div><div>Sun</div></div><div class="monthly-day-wrap"></div>');
		} else {
			console.log('Incorrect entry for weekStart variable.')
		}

		// 日历头。月份切换
		$('#' + uniqueId).prepend('<div class="monthly-header"><div class="monthly-header-title"></div><a href="#" class="monthly-prev"></a><a href="#" class="monthly-next"></a></div>').append('<div class="monthly-event-list"></div>');

		// 这个月有多少天？
		function daysInMonth(m, y){
			return m===2?y&3||!(y%25)&&y&15?28:29:30+(m+(m>>3)&1);
		}

		// 设置当前年月.data()函数用于在当前jQuery对象所匹配的所有元素上存取数据。通过data()函数存取的数据都是临时数据，一旦页面刷新，之前存放的数据都将不复存在。
		function setMonthly(m, y){
			$('#' + uniqueId).data('setMonth', m).data('setYear', y);
			$('#' + uniqueId+ ' .monthly-event-list').prepend('bro.');

			// 一个月中有多少天
			var dayQty = daysInMonth(m, y),
				// Get day of the week the first day is
				mZeroed = m -1,
				firstDay = new Date(y, mZeroed, 1, 0, 0, 0, 0).getDay();

			// Remove old days
			$('#' + uniqueId + ' .monthly-day, #' + uniqueId + ' .monthly-day-blank').remove();
			$('#'+uniqueId+' .monthly-event-list').empty();
			// Print out the days
			if (options.mode == 'event') {
				for(var i = 0; i < dayQty; i++) {
					
					var day = i + 1; // Fix 0 indexed days
					var dayNamenum = new Date(y, mZeroed, day, 0, 0, 0, 0).getDay()
					$('#' + uniqueId + ' .monthly-day-wrap').append(
							"<div  name = 'listdiv' class='monthly-day monthly-day-event' data-number='"+day+"'>"
							+"<div  name = 'showdatediv' class='monthly-day-number'>"+day	
							 
							+"</div>" 
							
							+"<div class='showresult' name = 'showresult' value = ''>"	
							+"</div>" 
							+"<div  name = 'showselectdiv' class='showselectdiv' style = 'display:none' value = ''>"
								 +"<div name = 'selectdiv' class = 'selectdiv' style= 'background: #FFFF00'>3内勤</div>"
								 +"<div name = 'selectdiv' class = 'selectdiv' style= 'background: #87CEFA'>外勤</div>"
								 +"<div name = 'selectdiv' class = 'selectdiv' style= 'background: #C0C0C0'>请假</div>"
								 +"<div name = 'selectdiv' class = 'selectdiv' style= 'background: #FFDEAD'>旷工</div>"
							+"</div>" 
							+"<div  name = 'showresult' class='showresult' style = 'display:none'> </div>" 
					+"</div>" 
					);
				}
			} else {
				for(var i = 0; i < dayQty; i++) {
					// Fix 0 indexed days
					var day = i + 1;

					// Check if it's a day in the past
					if(((day < currentDay && m === currentMonth) || y < currentYear || (m < currentMonth && y == currentYear)) && options.stylePast == true){
							$('#' + uniqueId + ' .monthly-day-wrap').append('<a href="#" class="monthly-day monthly-day-pick monthly-past-day" data-number="'+day+'"><div class="monthly-day-number">'+day+'</div><div class="monthly-indicator-wrap"></div></a>');
					} else {
						$('#' + uniqueId + ' .monthly-day-wrap').append('<a href="#" class="monthly-day monthly-day-pick" data-number="'+day+'"><div class="monthly-day-number">'+day+'</div><div class="monthly-indicator-wrap"></div></a>');
					}
				}
			}
			

			//设置当前日期
			var setMonth = $('#' + uniqueId).data('setMonth'),
				setYear = $('#' + uniqueId).data('setYear');
			//当前日期打红圈
			if (setMonth == currentMonth && setYear == currentYear) {
				$('#' + uniqueId + ' *[data-number="'+currentDay+'"]').addClass('monthly-today');
			}

			// 重置按钮，返回到当前日期
			if (setMonth == currentMonth && setYear == currentYear) {
				$('#' + uniqueId + ' .monthly-header-title').html(monthNames[m - 1] +' '+ y);
			} else {
				$('#' + uniqueId + ' .monthly-header-title').html(monthNames[m - 1] +' '+ y +'<a href="#" class="monthly-reset" title="Back To This Month"></a> ');
			}
			

			//当前日历前面多少格灰格
			if(options.weekStart == 'Sun' && firstDay != 7) {
				for(var i = 0; i < firstDay; i++) {
					$('#' + uniqueId + ' .monthly-day-wrap').prepend('<div class="monthly-day-blank"><div class="monthly-day-number"></div></div>');
				}
			} else if (options.weekStart == 'Mon' && firstDay != 1) {
				for(var i = 0; i < (firstDay - 1); i++) {
					$('#' + uniqueId + ' .monthly-day-wrap').prepend('<div class="monthly-day-blank" ><div class="monthly-day-number"></div></div>');
				}
			}

			//当前日历后面多少格灰格
			var numdays = $('#' + uniqueId + ' .monthly-day').length,
				numempty = $('#' + uniqueId + ' .monthly-day-blank').length,
				totaldays = numdays + numempty,
				roundup = Math.ceil(totaldays/7) * 7,
				daysdiff = roundup - totaldays;
			if(totaldays % 7 != 0) {
				for(var i = 0; i < daysdiff; i++) {
					$('#' + uniqueId + ' .monthly-day-wrap').append('<div class="monthly-day-blank"><div class="monthly-day-number"></div></div>');
				}
			} 
				 			
		}

		// 设置年月
		setMonthly(currentMonth, currentYear);
 

		// 月视图  前进
		$(document.body).on('click', '#'+uniqueId+' .monthly-next', function (e) {
			var setMonth = $('#' + uniqueId).data('setMonth'),
				setYear = $('#' + uniqueId).data('setYear');
			if (setMonth == 12) {
				var newMonth = 1,
					newYear = setYear + 1;
				setMonthly(newMonth, newYear);
			} else {
				var newMonth = setMonth + 1,
					newYear = setYear;
				setMonthly(newMonth, newYear);
			}
		 
			e.preventDefault();
		});

		// 月视图  后退
		$(document.body).on('click', '#'+uniqueId+' .monthly-prev', function (e) {
			var setMonth = $('#' + uniqueId).data('setMonth'),
				setYear = $('#' + uniqueId).data('setYear');
			if (setMonth == 1) {
				var newMonth = 12,
					newYear = setYear - 1;
				setMonthly(newMonth, newYear);
			} else {
				var newMonth = setMonth - 1,
					newYear = setYear;
				setMonthly(newMonth, newYear);
			}
		 
			e.preventDefault();
		});

		// 	 月视图  重置
		$(document.body).on('click', '#'+uniqueId+' .monthly-reset', function (e) {
			setMonthly(currentMonth, currentYear);
			
			e.preventDefault();
			e.stopPropagation();
		});

		// Back to month view  不知道是什么。可以删
		$(document.body).on('click', '#'+uniqueId+' .monthly-cal', function (e) {
			$(this).remove();
			$('#' + uniqueId + ' .monthly-day-wrap, #'+uniqueId+' .monthly-day-title-wrap').css({
					'filter':'blur(0px)',
					'webkitFilter':'blur(0px)'
				});
				$('#' + uniqueId+' .monthly-event-list').css('transform','scale(0)').delay('800').hide();
			e.preventDefault();
		});

		// 日。点击动作
		$(document.body).on('click', '#'+uniqueId+' a.monthly-day', function (e) {
		 
		});
		
		// Clicking an event within the list  不知道是什么，可以删
		$(document.body).on('click', '#'+uniqueId+' .listed-event', function (e) {
			var href = $(this).attr('href');
			// If there isn't a link, don't go anywhere
			if(!href) {
				e.preventDefault();
			}
		});

	
		$("div[name='selectdiv']").unbind('click').bind('click',function(){	 
			$(this).parent().val($(this).text());
		
		})
		
		$('#' + uniqueId + " .monthly-day-wrap div[name='listdiv']").hover(function(){	
			$(this).children("div[name='showdatediv']").css('display','none');
			$(this).children("div[name='showselectdiv']").css('display','block');
		},function(){
		 
			if($(this).children("div[name='showresult']").val() != $(this).children("div[name='showselectdiv']").val()){
				$(this).children("div[name='showresult']").empty();
				$(this).children("div[name='showresult']").append($(this).children("div[name='showselectdiv']").val());
				$(this).children("div[name='showresult']").val($(this).children("div[name='showselectdiv']").val());
			}
			$(this).children("div[name='showdatediv']").css('display','block');
			$(this).children("div[name='showselectdiv']").css('display','none');
			
		});
			
		
		}
	});
})(jQuery);
