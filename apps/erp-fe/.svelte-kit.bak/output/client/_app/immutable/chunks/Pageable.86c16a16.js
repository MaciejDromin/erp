import{s as x,j as C,r as M,o as $,k as tt}from"./scheduler.f526bf47.js";import{S as et,i as nt,q as R,g as P,r as z,s as B,m as U,h as j,j as S,u as at,f as w,c as I,n as G,k as h,a as st,x as d,v as F,C as H,G as K,p as ot,t as W,w as X,b as it,d as Y,o as lt}from"./index.69537f49.js";function ct(e,s){const n={},t={},_={$$scope:1};let a=e.length;for(;a--;){const o=e[a],f=s[a];if(f){for(const i in o)i in f||(t[i]=1);for(const i in f)_[i]||(n[i]=f[i],_[i]=1);e[a]=f}else for(const i in o)_[i]=1}for(const o in t)o in n||(n[o]=void 0);return n}const rt=async(e,s,n)=>await fetch("/api"+e,{method:s,body:JSON.stringify(n),headers:{Accept:"application/json","Content-Type":"application/json"}});var Z=(e=>(e.GET="GET",e.POST="POST",e.PUT="PUT",e.PATCH="PATCH",e.DELETE="DELETE",e))(Z||{});function ut(e){let s,n,t,_,a,o,f,i,b,m,D,v=e[2]+1+"",E,c,u,O,k,y,N,L;const q=[{data:e[4]}];var T=e[0];function V(l){let r={};for(let g=0;g<q.length;g+=1)r=tt(r,q[g]);return{props:r}}return T&&(t=R(T,V())),{c(){s=P("div"),n=P("div"),t&&z(t.$$.fragment),_=B(),a=P("div"),o=P("button"),f=U("«"),b=B(),m=P("button"),D=U("Page "),E=U(v),c=B(),u=P("button"),O=U("»"),this.h()},l(l){s=j(l,"DIV",{class:!0});var r=S(s);n=j(r,"DIV",{class:!0});var g=S(n);t&&at(t.$$.fragment,g),g.forEach(w),_=I(r),a=j(r,"DIV",{class:!0});var p=S(a);o=j(p,"BUTTON",{class:!0});var J=S(o);f=G(J,"«"),J.forEach(w),b=I(p),m=j(p,"BUTTON",{class:!0});var A=S(m);D=G(A,"Page "),E=G(A,v),A.forEach(w),c=I(p),u=j(p,"BUTTON",{class:!0});var Q=S(u);O=G(Q,"»"),Q.forEach(w),p.forEach(w),r.forEach(w),this.h()},h(){h(n,"class","my-3"),h(o,"class",i=C(`join-item btn disabled:opacity-75 ${e[6](e[2])?"":"btn-disable opacity-75"}`)+" svelte-12glawk"),h(m,"class","join-item btn btn-disable svelte-12glawk"),h(u,"class",k=C(`join-item btn disabled:opacity-75 ${e[7](e[2],e[3])?"":"btn-disable opacity-75"}`)+" svelte-12glawk"),h(a,"class","join flex mx-auto"),h(s,"class","flex flex-col")},m(l,r){st(l,s,r),d(s,n),t&&F(t,n,null),d(s,_),d(s,a),d(a,o),d(o,f),d(a,b),d(a,m),d(m,D),d(m,E),d(a,c),d(a,u),d(u,O),y=!0,N||(L=[H(o,"click",K(e[9])),H(u,"click",K(e[10]))],N=!0)},p(l,[r]){const g=r&16?ct(q,[{data:l[4]}]):{};if(r&1&&T!==(T=l[0])){if(t){ot();const p=t;W(p.$$.fragment,1,0,()=>{X(p,1)}),it()}T?(t=R(T,V()),z(t.$$.fragment),Y(t.$$.fragment,1),F(t,n,null)):t=null}else T&&t.$set(g);(!y||r&4&&i!==(i=C(`join-item btn disabled:opacity-75 ${l[6](l[2])?"":"btn-disable opacity-75"}`)+" svelte-12glawk"))&&h(o,"class",i),(!y||r&4)&&v!==(v=l[2]+1+"")&&lt(E,v),(!y||r&12&&k!==(k=C(`join-item btn disabled:opacity-75 ${l[7](l[2],l[3])?"":"btn-disable opacity-75"}`)+" svelte-12glawk"))&&h(u,"class",k)},i(l){y||(t&&Y(t.$$.fragment,l),y=!0)},o(l){t&&W(t.$$.fragment,l),y=!1},d(l){l&&w(s),t&&X(t),N=!1,M(L)}}}function ft(e,s,n){let{endpoint:t}=s,{component:_}=s,{additionalSearch:a=void 0}=s,o=0,f=0,i;const b=async(c,u)=>{let O=u===void 0?"":u;const k=await rt(t+"?page="+c+O,Z.GET);n(4,i=await k.json()),console.log(i),n(3,f=i.totalPages)};$(async()=>{b(o,a)});const m=c=>c!=0,D=(c,u)=>!(c>=u-1),v=()=>b(n(2,--o),a),E=()=>b(n(2,++o),a);return e.$$set=c=>{"endpoint"in c&&n(8,t=c.endpoint),"component"in c&&n(0,_=c.component),"additionalSearch"in c&&n(1,a=c.additionalSearch)},[_,a,o,f,i,b,m,D,t,v,E]}class mt extends et{constructor(s){super(),nt(this,s,ft,ut,x,{endpoint:8,component:0,additionalSearch:1})}}export{Z as H,mt as P};
