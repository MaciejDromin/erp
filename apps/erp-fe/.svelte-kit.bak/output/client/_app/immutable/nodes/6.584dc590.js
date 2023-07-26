import{s as Bt,n as bt,f as Ut,e as Yt,o as Gt,h as Kt}from"../chunks/scheduler.f526bf47.js";import{S as zt,i as Rt,g as d,s as x,h as f,j as g,z as H,c as I,f as v,k as m,a as nt,x as l,e as Nt,y as Ft,m as G,n as K,o as Q,r as gt,u as Ct,B as At,v as xt,C as Qt,d as It,t as $t,w as jt,D as Mt,F as Wt}from"../chunks/index.69537f49.js";import{e as rt}from"../chunks/each.e59479a4.js";import{i as pt,I as Xt}from"../chunks/ItemTable.404bf38a.js";import{M as Zt}from"../chunks/Modal.aaaa625c.js";import{H as te,P as Jt}from"../chunks/Pageable.86c16a16.js";function Ht(s,e,a){const t=s.slice();return t[5]=e[a],t}function St(s){let e,a=rt(s[0].content),t=[];for(let n=0;n<a.length;n+=1)t[n]=Lt(Ht(s,a,n));return{c(){for(let n=0;n<t.length;n+=1)t[n].c();e=Nt()},l(n){for(let u=0;u<t.length;u+=1)t[u].l(n);e=Nt()},m(n,u){for(let o=0;o<t.length;o+=1)t[o]&&t[o].m(n,u);nt(n,e,u)},p(n,u){if(u&7){a=rt(n[0].content);let o;for(o=0;o<a.length;o+=1){const r=Ht(n,a,o);t[o]?t[o].p(r,u):(t[o]=Lt(r),t[o].c(),t[o].m(e.parentNode,e))}for(;o<t.length;o+=1)t[o].d(1);t.length=a.length}},d(n){n&&v(e),Ft(t,n)}}}function Lt(s){let e,a,t=s[5].uuid+"",n,u,o,r=s[2](s[1],s[5].objectId)+"",i,h,_,E=s[5].amount.value+"",A,q,T,O=s[5].amount.currencyCode+"",S,w;return{c(){e=d("tr"),a=d("td"),n=G(t),u=x(),o=d("td"),i=G(r),h=x(),_=d("td"),A=G(E),q=x(),T=d("td"),S=G(O),w=x()},l(j){e=f(j,"TR",{});var b=g(e);a=f(b,"TD",{});var D=g(a);n=K(D,t),D.forEach(v),u=I(b),o=f(b,"TD",{});var k=g(o);i=K(k,r),k.forEach(v),h=I(b),_=f(b,"TD",{});var at=g(_);A=K(at,E),at.forEach(v),q=I(b),T=f(b,"TD",{});var W=g(T);S=K(W,O),W.forEach(v),w=I(b),b.forEach(v)},m(j,b){nt(j,e,b),l(e,a),l(a,n),l(e,u),l(e,o),l(o,i),l(e,h),l(e,_),l(_,A),l(e,q),l(e,T),l(T,S),l(e,w)},p(j,b){b&1&&t!==(t=j[5].uuid+"")&&Q(n,t),b&3&&r!==(r=j[2](j[1],j[5].objectId)+"")&&Q(i,r),b&1&&E!==(E=j[5].amount.value+"")&&Q(A,E),b&1&&O!==(O=j[5].amount.currencyCode+"")&&Q(S,O)},d(j){j&&v(e)}}}function ee(s){let e,a,t,n="<tr><th>ID</th> <th>Object Name</th> <th>Amount</th> <th>Currency</th></tr>",u,o,r=s[0]!==void 0&&St(s);return{c(){e=d("div"),a=d("table"),t=d("thead"),t.innerHTML=n,u=x(),o=d("tbody"),r&&r.c(),this.h()},l(i){e=f(i,"DIV",{class:!0});var h=g(e);a=f(h,"TABLE",{class:!0});var _=g(a);t=f(_,"THEAD",{class:!0,["data-svelte-h"]:!0}),H(t)!=="svelte-ogqtx0"&&(t.innerHTML=n),u=I(_),o=f(_,"TBODY",{});var E=g(o);r&&r.l(E),E.forEach(v),_.forEach(v),h.forEach(v),this.h()},h(){m(t,"class","text-primary-content"),m(a,"class","table"),m(e,"class","overflow-x-auto text-primary-content mx-auto")},m(i,h){nt(i,e,h),l(e,a),l(a,t),l(a,u),l(a,o),r&&r.m(o,null)},p(i,[h]){i[0]!==void 0?r?r.p(i,h):(r=St(i),r.c(),r.m(o,null)):r&&(r.d(1),r=null)},i:bt,o:bt,d(i){i&&v(e),r&&r.d()}}}function ae(s,e,a){let{data:t=void 0}=e,n={};const u=async i=>{if(i===void 0)return;const h=await fetch("/api/inventory/items/object-names",{method:te.POST,body:JSON.stringify({itemIds:o(i.content)})});a(1,n=await h.json())},o=i=>{let h=[];return i.forEach(_=>h.push(_.objectId)),h},r=(i,h)=>i[h];return s.$$set=i=>{"data"in i&&a(0,t=i.data)},s.$$.update=()=>{s.$$.dirty&1&&t&&u(t)},[t,n,r]}class le extends zt{constructor(e){super(),Rt(this,e,ae,ee,Bt,{data:0})}}function Pt(s,e,a){const t=s.slice();return t[5]=e[a],t}function qt(s){let e,a;return{c(){e=d("option"),this.h()},l(t){e=f(t,"OPTION",{}),g(e).forEach(v),this.h()},h(){e.__value=a=s[5],Mt(e,e.__value)},m(t,n){nt(t,e,n)},p(t,n){n&2&&a!==(a=t[5])&&(e.__value=a,Mt(e,e.__value))},d(t){t&&v(e)}}}function ne(s){let e,a;return e=new Jt({props:{endpoint:"/inventory/items",component:Xt}}),{c(){gt(e.$$.fragment)},l(t){Ct(e.$$.fragment,t)},m(t,n){xt(e,t,n),a=!0},p:bt,i(t){a||(It(e.$$.fragment,t),a=!0)},o(t){$t(e.$$.fragment,t),a=!1},d(t){jt(e,t)}}}function se(s){let e,a,t,n,u="$",o,r,i="Total Amount",h,_,E=s[0].objectsValue.totalAmount.value+"",A,q,T=s[0].objectsValue.totalAmount.currencyCode+"",O,S,w,j="Total amount of all objects",b,D,k,at="0..*",W,B,Dt="Objects Count",it,X,lt=s[0].objectsValue.objectsCount+"",st,ct,z,Et="How much objects were counted",ut,M,R,yt='<input name="amount" type="text" placeholder="Amount" class="input input-bordered input-primary w-full max-w-xs"/> <input name="currencyCode" type="text" placeholder="Currency Code" class="input input-bordered input-primary w-full max-w-xs"/>',dt,N,y,ft,Z,L,mt,F,Vt="Add Row",vt,J,U,ht,Tt,tt=rt(s[1]),$=[];for(let c=0;c<tt.length;c+=1)$[c]=qt(Pt(s,tt,c));return L=new Zt({props:{modalId:"item_modal",buttonName:s[2](s[1]),$$slots:{default:[ne]},$$scope:{ctx:s}}}),J=new Jt({props:{endpoint:"/finances/object-value",component:le}}),{c(){e=d("div"),a=d("div"),t=d("div"),n=d("div"),n.textContent=u,o=x(),r=d("div"),r.textContent=i,h=x(),_=d("div"),A=G(E),q=x(),O=G(T),S=x(),w=d("div"),w.textContent=j,b=x(),D=d("div"),k=d("div"),k.textContent=at,W=x(),B=d("div"),B.textContent=Dt,it=x(),X=d("div"),st=G(lt),ct=x(),z=d("div"),z.textContent=Et,ut=x(),M=d("form"),R=d("div"),R.innerHTML=yt,dt=x(),N=d("div"),y=d("select");for(let c=0;c<$.length;c+=1)$[c].c();ft=x(),Z=d("div"),gt(L.$$.fragment),mt=x(),F=d("button"),F.textContent=Vt,vt=x(),gt(J.$$.fragment),this.h()},l(c){e=f(c,"DIV",{id:!0,class:!0});var C=g(e);a=f(C,"DIV",{class:!0});var V=g(a);t=f(V,"DIV",{class:!0});var p=g(t);n=f(p,"DIV",{class:!0,["data-svelte-h"]:!0}),H(n)!=="svelte-1g0b4w2"&&(n.textContent=u),o=I(p),r=f(p,"DIV",{class:!0,["data-svelte-h"]:!0}),H(r)!=="svelte-1aogcmk"&&(r.textContent=i),h=I(p),_=f(p,"DIV",{class:!0});var Y=g(_);A=K(Y,E),q=I(Y),O=K(Y,T),Y.forEach(v),S=I(p),w=f(p,"DIV",{class:!0,["data-svelte-h"]:!0}),H(w)!=="svelte-1g9muqb"&&(w.textContent=j),p.forEach(v),b=I(V),D=f(V,"DIV",{class:!0});var P=g(D);k=f(P,"DIV",{class:!0,["data-svelte-h"]:!0}),H(k)!=="svelte-1rbgisp"&&(k.textContent=at),W=I(P),B=f(P,"DIV",{class:!0,["data-svelte-h"]:!0}),H(B)!=="svelte-15amjvl"&&(B.textContent=Dt),it=I(P),X=f(P,"DIV",{class:!0});var wt=g(X);st=K(wt,lt),wt.forEach(v),ct=I(P),z=f(P,"DIV",{class:!0,["data-svelte-h"]:!0}),H(z)!=="svelte-1kqpz1p"&&(z.textContent=Et),P.forEach(v),V.forEach(v),ut=I(C),M=f(C,"FORM",{method:!0,class:!0});var ot=g(M);R=f(ot,"DIV",{class:!0,["data-svelte-h"]:!0}),H(R)!=="svelte-1iesgvg"&&(R.innerHTML=yt),dt=I(ot),N=f(ot,"DIV",{class:!0});var et=g(N);y=f(et,"SELECT",{name:!0,class:!0});var Ot=g(y);for(let _t=0;_t<$.length;_t+=1)$[_t].l(Ot);Ot.forEach(v),ft=I(et),Z=f(et,"DIV",{class:!0});var kt=g(Z);Ct(L.$$.fragment,kt),kt.forEach(v),mt=I(et),F=f(et,"BUTTON",{class:!0,["data-svelte-h"]:!0}),H(F)!=="svelte-1q26zcv"&&(F.textContent=Vt),et.forEach(v),ot.forEach(v),vt=I(C),Ct(J.$$.fragment,C),C.forEach(v),this.h()},h(){m(n,"class","stat-figure text-primary text-4xl"),m(r,"class","stat-title"),m(_,"class","stat-value text-primary"),m(w,"class","stat-desc"),m(t,"class","stat"),m(k,"class","stat-figure text-secondary text-3xl"),m(B,"class","stat-title"),m(X,"class","stat-value text-secondary"),m(z,"class","stat-desc"),m(D,"class","stat"),m(a,"class","stats shadow mx-auto"),m(R,"class","flex flex-row gap-3"),y.multiple=!0,m(y,"name","itemId"),m(y,"class","p-4 mr-auto hidden"),s[1]===void 0&&Ut(()=>s[3].call(y)),m(Z,"class","mr-auto"),m(F,"class","btn btn-primary"),m(N,"class","flex flex-row gap-3"),m(M,"method","POST"),m(M,"class","mx-auto flex flex-col gap-3 py-6"),m(e,"id","item"),m(e,"class","flex flex-col gap-3 px-10 pt-10")},m(c,C){nt(c,e,C),l(e,a),l(a,t),l(t,n),l(t,o),l(t,r),l(t,h),l(t,_),l(_,A),l(_,q),l(_,O),l(t,S),l(t,w),l(a,b),l(a,D),l(D,k),l(D,W),l(D,B),l(D,it),l(D,X),l(X,st),l(D,ct),l(D,z),l(e,ut),l(e,M),l(M,R),l(M,dt),l(M,N),l(N,y);for(let V=0;V<$.length;V+=1)$[V]&&$[V].m(y,null);At(y,s[1]),l(N,ft),l(N,Z),xt(L,Z,null),l(N,mt),l(N,F),l(e,vt),xt(J,e,null),U=!0,ht||(Tt=Qt(y,"change",s[3]),ht=!0)},p(c,[C]){if((!U||C&1)&&E!==(E=c[0].objectsValue.totalAmount.value+"")&&Q(A,E),(!U||C&1)&&T!==(T=c[0].objectsValue.totalAmount.currencyCode+"")&&Q(O,T),(!U||C&1)&&lt!==(lt=c[0].objectsValue.objectsCount+"")&&Q(st,lt),C&2){tt=rt(c[1]);let p;for(p=0;p<tt.length;p+=1){const Y=Pt(c,tt,p);$[p]?$[p].p(Y,C):($[p]=qt(Y),$[p].c(),$[p].m(y,null))}for(;p<$.length;p+=1)$[p].d(1);$.length=tt.length}C&2&&At(y,c[1]);const V={};C&2&&(V.buttonName=c[2](c[1])),C&256&&(V.$$scope={dirty:C,ctx:c}),L.$set(V)},i(c){U||(It(L.$$.fragment,c),It(J.$$.fragment,c),U=!0)},o(c){$t(L.$$.fragment,c),$t(J.$$.fragment,c),U=!1},d(c){c&&v(e),Ft($,c),jt(L),jt(J),ht=!1,Tt()}}}function oe(s,e,a){let t;Yt(s,pt,i=>a(4,t=i));let{data:n}=e,u=[];Gt(()=>{Kt(pt,t=[],t)}),pt.subscribe(i=>{a(1,u=[...i]),a(1,u)});const o=i=>i.length===0?"select item":`${i.length} item selected`;function r(){u=Wt(this),a(1,u)}return s.$$set=i=>{"data"in i&&a(0,n=i.data)},[n,u,o,r]}class me extends zt{constructor(e){super(),Rt(this,e,oe,se,Bt,{data:0})}}export{me as component};
