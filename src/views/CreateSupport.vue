<template>
  <div class="register">
    <div class="inner">
      <!-- <form @submit.prevent="MasterHandler"> -->
      <h1>Create a New Support staff</h1>

      <label>UserName</label><br />
      <input
        type="text"
        placeholder="UserName"
        v-model="formData.userName"
      /><br />
      <label>Phone Number</label><br />
      <input
        type="text"
        placeholder="Phone Number"
        v-model="formData.phoneNumber"
      /><br />
      <label>Email</label><br />
      <input type="text" placeholder="Email" v-model="formData.emailId" /><br />
      <label>Password</label><br />
      <input
        type="password"
        placeholder="Password"
        v-model="formData.password"
      /><br /><br />
      <button @click="MasterHandler" class="button-19">Register</button><br />
      <!-- </form> -->
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      formData: {
        userName: "",
        fullName: "",
        emailId: "",
        password: "",
        phoneNumber: "",
        listSupport: "",
      },
    };
  },
  computed: {
    ...mapGetters(["getSupportEnginner"]),
  },
  methods: {
    ...mapActions(["ADD_SUPPORT"]),
    MasterHandler() {
      const requestBody = {
        contactNumber: this.formData.phoneNumber,
        emailId: this.formData.emailId,
        password: this.formData.password,
        supportName: this.formData.userName,
        isAdmin: false,
      };
      this.$store
        .dispatch("ADD_SUPPORT", {
          payload: requestBody,
          success: (res) => {
            this.listSupport = res;
            if (this.listSupport === true) {
              this.$toasted.success("Account Created", {
                duration: 2000,
                position: "top-center",
              });
            }
            this.formData.userName = "";
            this.formData.fullName = "";
            this.formData.emailId = "";
            this.formData.password = "";
            this.formData.phoneNumber = "";
          },
        })
        .catch((error) => {
          this.$toasted.error("Please enter Valid details", {
            duration: 2000,
            position: "top-center",
          });
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
input {
  width: 300px;
  height: 40px;
}

.register {
  display: flex;
  justify-content: center;
  margin: 30px 0 100px 0;
  background-color: white;
}

.inner {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 350px;
  margin-right: 50px;
  padding: 30px 30px;
  border: 2px solid black;
  /* background-color: rgb(224, 126, 80); */
  /* transition: box-shadow 1s ease-in-out; */
}
label {
  font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
  font-size: 15px;
  /* margin-left: 0; */
}

.button-19 {
  appearance: button;
  background-color: #1899d6;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-block;
  font-family: din-round, sans-serif;
  font-size: 15px;
  font-weight: 700;
  letter-spacing: 0.8px;
  line-height: 20px;
  margin: 0;
  outline: none;
  overflow: visible;
  padding: 13px 16px;
  text-align: center;
  text-transform: uppercase;
  touch-action: manipulation;
  transform: translateZ(0);
  transition: filter 0.2s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
  white-space: nowrap;
  width: 200px;
}

.button-19:after {
  background-clip: padding-box;
  background-color: #1cb0f6;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  bottom: -4px;
  content: "";
  left: 0;
  position: absolute;
  right: 0;
  top: 0;
  z-index: -1;
}

.button-19:main,
.button-19:focus {
  user-select: auto;
}

.button-19:hover:not(:disabled) {
  filter: brightness(1.1);
  -webkit-filter: brightness(1.1);
}

.button-19:disabled {
  cursor: auto;
}
</style>
