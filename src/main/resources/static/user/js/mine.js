new Vue({
    el:'#container',
    data:{
        movie_list:[
           
        ],
        message:null,
    },
    mounted(){
        axios.get('http://localhost:8081/moive/getHotMovies')
            .then((response)=> {
                console.log(response);
                mlist=response.data.data.movies;
                for(j = 0,len=mlist.length; j < len; j++) {
                    m=mlist[j];
                    mid=m.id;                           
                    var temp={src:'movie-page-full.html',imgsrc:'images/indexpic/c'+mid+'.jpg',title:m.name,time:'169mins',option:'科幻｜剧情',rating:m.score};

                    this.movie_list.push(temp);
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
            axios.post('http://localhost:8081/cinema/cinemaFliter', params)
            .then((response)=>  {
                axios.get('http://localhost:8081/cinema/getScheduals', {
                    params: {
                    cinema_id: response.data.data.cinemas[0].id
                    }
                })
                .then((response)=> {
                    mlist=response.data.data;
                    console.log(mlist);
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        m=mlist[j].movie;
                        mid=m.id;                           
                        var temp={src:'movie-page-full.html',imgsrc:'images/indexpic/c'+mid+'.jpg',title:m.name,time:'169mins',option:'科幻｜剧情',rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            })
            .catch(function (error) {
                console.log(error);
            });
        },
        search_category:function(m){
            var k=document.getElementsByName("search-input")[0];
            alert(k.value);
        },
        search_actors:function(m){
            var k=document.getElementsByName("search-input")[0];
            const params = new URLSearchParams();
            params.append('name', k.value);
            params.append('role','actor');
            axios.post('http://localhost:8081//movie/directorFilter', params)
            .then((response)=>  {
                axios.get('http://localhost:8081/cinema/getScheduals', {
                    params: {
                    cinema_id: response.data.data.cinemas[0].id
                    }
                })
                .then((response)=> {
                    mlist=response.data.data;
                    console.log(mlist);
                    this.movie_list=[];
                    for(j = 0,len=mlist.length; j < len; j++) {
                        m=mlist[j].movie;
                        mid=m.id;                           
                        var temp={src:'movie-page-full.html',imgsrc:'images/indexpic/c'+mid+'.jpg',title:m.name,time:'169mins',option:'科幻｜剧情',rating:m.score};
                        this.movie_list.push(temp);
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
            })
            .catch(function (error) {
                console.log(error);
            });
        },
        search_director:function(m){
            var k=document.getElementsByName("search-input")[0];
            alert(k.value);
        },
        search_country:function(m){
            var k=document.getElementsByName("search-input")[0];
            alert(k.value);
        },
    }
})