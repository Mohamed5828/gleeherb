import postData from "./DataPosting";

async function MakeCODRequest(disName, userToken) {
  try {
    await postData("codpayment", { discountName: disName }, userToken);
  } catch (error) {
    console.error("Error Placeing Order", error);
  }
}

export default MakeCODRequest;
