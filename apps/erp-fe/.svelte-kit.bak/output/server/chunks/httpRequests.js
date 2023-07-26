const unsecuredExternalApiRequest = async (endpoint, method, body) => {
  const ret = await fetch("http://127.0.0.1:8080" + endpoint, {
    method,
    body: JSON.stringify(body),
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    }
  }).catch((e) => {
    console.log(e);
    return new Response(null, { status: 503 });
  });
  return ret;
};
export {
  unsecuredExternalApiRequest as u
};
