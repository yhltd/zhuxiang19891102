function dcMenu(options){
    let el = options.el;
    let list = options.list;
    $(el).addClass('dc-menu');
    for(let i = 0;i<list.length;i++){
        let item = list[i];
        let menuItem = $('<div class="dc-menu-item"></div>');
        let menuLink = $('<div class="dc-menu-link">'+item.title+'</div>');
        let menuI =$('<i class="bi bi-chevron-down dc-icon i-top"></i>')
        let isHasChildren = item.children!=undefined;
        if(isHasChildren){
            $(menuLink).append(menuI);
            $(menuLink).click(function(){
                let isActive = $(this).hasClass('is-active')
                if(isActive){
                    $(this).removeClass('is-active')
                    onSelect_(isHasChildren,$(this),item.children.length);
                }else{
                    linkClose(list,isHasChildren);
                    $(this).addClass('is-active')
                    onSelect_(isHasChildren,$(this),item.children.length);
                }
            })
        }else{
            $(menuLink).click(function(){
                linkClose(list,false);
                onSelect_(isHasChildren,$(this),0);
                options.onSelect(item);
            })
        }
        $(menuItem).append(menuLink)
        $(el).append(menuItem)

        if(isHasChildren){
            let menuUl = $('<ul class="dc-menu-ul">')
            for(let j =0;j<item.children.length;j++){
                let menuLi = $('<li class="dc-menu-li">'+item.children[j].text+'</li>')
                $(menuLi).click(function(){
                    $('.dc-menu-li').each(function(index,li){
                        $(li).removeClass('is-active');
                    })
                    $(this).addClass('is-active');
                    options.onSelect(item.children[j]);
                })
                $(menuUl).append(menuLi)
            }
            $(menuUl).css({
                'display': 'none',
                'height': 0
            })
            $(menuItem).append(menuUl)
        }
    }
}

function onSelect_(isHasChildren,item,childrenLength){
    let ul = $(item).next();
    if($(item).hasClass('is-active')){
        if(isHasChildren){
            ulShow(ul,childrenLength*40);
        }
    }else{
        if(isHasChildren){
            ulHide(ul);
        }
    }
}

function linkClose(list,isHasChildren){
    $('.dc-menu-link').each(function(index,link){
        let isActive = $(link).hasClass('is-active')
        let isHasChildren = list[index].children != undefined
        if(isActive && isHasChildren){
            if(!isHasChildren){
                $(link).next().children().each(function(i,li){
                    if($(li).hasClass('is-active')){
                        $(li).removeClass('is-active')
                    }
                })
            }

            $(link).removeClass('is-active');
            onSelect_(isHasChildren,link,list[index].children.length);
        }
    })
}

function ulHide($ul){
    let height = $($ul).height();
    $($ul).css({
        'color': '#ffffff',
        'height': height,
    }).animate({
        'height': '0'
    },300,'swing',function(){
        $($ul).css('display','none')
    })
}

function ulShow($ul,height){
    $($ul).css('display','block').css({
        'color': '#000000'
    }).animate({
        'height': height+'px'
    },300,'swing',function(){
        $($ul).css('height',height+'px')
    });
}