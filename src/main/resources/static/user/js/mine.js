    new Vue({
        el:'#container',
        data:{
            movie_list:[

            ],
            hot_movie_list:[

            ],
            message:null,
        },
        mounted(){
            axios.post('http://localhost:8081/moive/Fliter/details',{
                "state":"on",
                "pageNumber": 0,
                "pageSize": 100,
                })
                .then((response)=> {
                    console.log(response);
                    mlist=response.data.data.cinemas_infos;
                    for(j = 0,len=mlist.length; j < len; j++) {
                        m=mlist[j];
                        mid=m.id;
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score};

                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            axios.get('http://localhost:8081/moive/getHotMovies')
                .then((response)=> {
                    mlist=response.data.data.movies;
                    console.log(mlist);
                    this.hot_movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        var m=mlist[j];
                        mid=m.id;
                        var rtime=m.releaseTime.split('T')[0];
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score,rtime:rtime};
                        this.hot_movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        methods:{
            search_cinema:function(m){
                var k=document.getElementsByName("search-input")[0];
                const params = new URLSearchParams();
                params.append('cinema_name', k.value);
                params.append('pageNumber', 0);
                params.append('pageSize', 100);
                axios.post('http://localhost:8081/moive/Fliter/details', {"cinema_name": k.value,"pageNumber": 0,"pageSize": 100,"state":"on"})
                .then((response)=>  {
                    console.log(response);
                    mlist=response.data.data.cinemas_infos;
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        var m=mlist[j];
                        mid=m.id;
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            search_category:function(m){
                var k=document.getElementsByName("search-input")[0];
                var tags=[];
                tags.push(k.value);
                axios.post('http://localhost:8081/moive/Fliter/brief', {"tags": tags,"pageNumber": 0,"pageSize": 100,"state":"on"})
                .then((response)=>  {
                    console.log(response);
                    mlist=response.data.data.movies;
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        var m=mlist[j];
                        mid=m.id;
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            search_actors:function(m){
                var k=document.getElementsByName("search-input")[0];
                const params = new URLSearchParams();
                params.append('name', k.value);
                params.append('role','actor');
                axios.post('http://localhost:8081/movie/staffFilter', params)
                .then((response)=>  {
                    console.log(response);
                    mlist=response.data.data.movies;
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        var m=mlist[j];
                        mid=m.id;
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            search_country:function(m){
                var k=document.getElementsByName("search-input")[0];
                axios.post('http://localhost:8081/moive/Fliter/details', {"country": k.value,"pageNumber": 0,"pageSize": 100,"state":"on"})
                .then((response)=>  {
                    console.log(response);
                    mlist=response.data.data.cinemas_infos;
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        var m=mlist[j];
                        mid=m.id;
                        var temp={src:'movie-page-full.html?id='+mid,imgsrc:m.showImage,title:m.name,time:m.duration,country:m.country,rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
        }
    })