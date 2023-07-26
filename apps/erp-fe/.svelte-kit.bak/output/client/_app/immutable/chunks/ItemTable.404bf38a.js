import{s as $,n as F,e as x,o as ee,i as te,h as ae}from"./scheduler.f526bf47.js";import{S as ne,i as le,g as m,s as k,h as v,j as b,z as se,c as y,f as _,k as L,a as z,x as h,e as G,y as oe,m as S,n as I,C as re,o as q}from"./index.69537f49.js";import{e as J}from"./each.e59479a4.js";import{w as ie}from"./index.0e775aa0.js";let de=[];const K=ie(de);function P(e,t,o){const a=e.slice();return a[8]=t[o],a}function W(e){let t,o=J(e[0].content),a=[];for(let l=0;l<o.length;l+=1)a[l]=X(P(e,o,l));return{c(){for(let l=0;l<a.length;l+=1)a[l].c();t=G()},l(l){for(let i=0;i<a.length;i+=1)a[i].l(l);t=G()},m(l,i){for(let n=0;n<a.length;n+=1)a[n]&&a[n].m(l,i);z(l,t,i)},p(l,i){if(i&63){o=J(l[0].content);let n;for(n=0;n<o.length;n+=1){const s=P(l,o,n);a[n]?a[n].p(s,i):(a[n]=X(s),a[n].c(),a[n].m(t.parentNode,t))}for(;n<a.length;n+=1)a[n].d(1);a.length=o.length}},d(l){l&&_(t),oe(a,l)}}}function X(e){let t,o,a=e[8].id+"",l,i,n,s=e[8].name+"",c,f,u,r=e[8].quantity+"",g,E,T,D=e[8].unit+"",M,B,w,C=e[2](e[8].categories)+"",N,H,A,j,O;function Z(){return e[6](e[8])}return{c(){t=m("tr"),o=m("td"),l=S(a),i=k(),n=m("td"),c=S(s),f=k(),u=m("td"),g=S(r),E=k(),T=m("td"),M=S(D),B=k(),w=m("td"),N=S(C),H=k(),this.h()},l(p){t=v(p,"TR",{class:!0});var d=b(t);o=v(d,"TD",{});var Q=b(o);l=I(Q,a),Q.forEach(_),i=y(d),n=v(d,"TD",{});var R=b(n);c=I(R,s),R.forEach(_),f=y(d),u=v(d,"TD",{});var U=b(u);g=I(U,r),U.forEach(_),E=y(d),T=v(d,"TD",{});var V=b(T);M=I(V,D),V.forEach(_),B=y(d),w=v(d,"TD",{});var Y=b(w);N=I(Y,C),Y.forEach(_),H=y(d),d.forEach(_),this.h()},h(){L(t,"class",A=`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${e[4](e[1],e[8].id)}
                ${e[5](e[1],e[8].id)}`)},m(p,d){z(p,t,d),h(t,o),h(o,l),h(t,i),h(t,n),h(n,c),h(t,f),h(t,u),h(u,g),h(t,E),h(t,T),h(T,M),h(t,B),h(t,w),h(w,N),h(t,H),j||(O=re(t,"click",Z),j=!0)},p(p,d){e=p,d&1&&a!==(a=e[8].id+"")&&q(l,a),d&1&&s!==(s=e[8].name+"")&&q(c,s),d&1&&r!==(r=e[8].quantity+"")&&q(g,r),d&1&&D!==(D=e[8].unit+"")&&q(M,D),d&1&&C!==(C=e[2](e[8].categories)+"")&&q(N,C),d&3&&A!==(A=`hover:bg-indigo-400 hover:text-black even:text-white hover:even:text-black hover:even:bg-indigo-400 cursor-pointer ease-in transition-all duration-200
                ${e[4](e[1],e[8].id)}
                ${e[5](e[1],e[8].id)}`)&&L(t,"class",A)},d(p){p&&_(t),j=!1,O()}}}function he(e){let t,o,a,l="<tr><th>ID</th> <th>Name</th> <th>Quantity</th> <th>Unit</th> <th>Categories</th></tr>",i,n,s=e[0]!==void 0&&W(e);return{c(){t=m("div"),o=m("table"),a=m("thead"),a.innerHTML=l,i=k(),n=m("tbody"),s&&s.c(),this.h()},l(c){t=v(c,"DIV",{class:!0});var f=b(t);o=v(f,"TABLE",{class:!0});var u=b(o);a=v(u,"THEAD",{class:!0,["data-svelte-h"]:!0}),se(a)!=="svelte-nqm7ao"&&(a.innerHTML=l),i=y(u),n=v(u,"TBODY",{});var r=b(n);s&&s.l(r),r.forEach(_),u.forEach(_),f.forEach(_),this.h()},h(){L(a,"class","text-primary-content"),L(o,"class","table"),L(t,"class","overflow-x-auto text-primary-content mx-auto")},m(c,f){z(c,t,f),h(t,o),h(o,a),h(o,i),h(o,n),s&&s.m(n,null)},p(c,[f]){c[0]!==void 0?s?s.p(c,f):(s=W(c),s.c(),s.m(n,null)):s&&(s.d(1),s=null)},i:F,o:F,d(c){c&&_(t),s&&s.d()}}}function ce(e,t,o){let a;x(e,K,r=>o(7,a=r));let{data:l=void 0}=t,i=new Map;ee(()=>{a.forEach(r=>i.set(r,"ok"))}),te(()=>{ae(K,a=Array.from(i.keys()),a)});const n=r=>r.map(E=>E.name).toString(),s=r=>{i.has(r)?i.delete(r):i.set(r,"ok"),o(1,i)},c=(r,g)=>r.has(g)?"bg-indigo-600 text-white":"",f=(r,g)=>r.has(g)?"even:bg-indigo-600":"even:bg-black",u=r=>s(r.id);return e.$$set=r=>{"data"in r&&o(0,l=r.data)},[l,i,n,s,c,f,u]}class ve extends ne{constructor(t){super(),le(this,t,ce,he,$,{data:0})}}export{ve as I,K as i};
