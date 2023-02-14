<template>
  <div>
    <!-- <nav-bar-1></nav-bar-1> -->
    <div class="box1">
      <label><b> Comments: </b></label>&nbsp;
      <!-- <p v-for="(comment, index) in comments" :key="index"> -->
      <input v-model="comments" type="text" name="comments" />&nbsp;
      <!-- </p> -->
      <button class="button-19" @click="commentHandler(comments)">Add</button>

      <label> <b> Docs:</b> </label>&nbsp;
      <!-- <div v-for="(doc,index) "> -->
      <input type="file" ref="input" @change="handleFileChange" accept="*" />
      <br />
      <button @click="onUpload" class="button-19">Upload</button>
      <br /><br />
      <button @click="upload" class="button-19">Save Doc</button>
    </div>
  </div>
</template>
<script>
// import NavBar1 from "@/components/NavBar1.vue";
import axios from "axios";
import { storage } from "@/firebase";
import { ref, uploadBytes, getDownloadURL } from "firebase/storage";
import Vue from "vue";
import Toasted from "vue-toasted";
Vue.use(Toasted);
export default {
  components: {
    // NavBar1,
  },
  data() {
    return {
      fileName: "",
      selectedFile: null,
      ticketId: "",
      comments: "",
      url: "",
      //   docs: "",
    };
  },
  //   methods: {
  // onFileSelected(event) {
  //   console.log(event);
  //   this.selectedFile = event.target.files[0];
  // },
  // onUpload() {
  //   const fd = new FormData();
  //   fd.append("image", this.selectedFile, this.selectedFile.name);
  //   axios.post("", fd).then((res) => {
  //     console.log(res);
  //   });
  // },

  //   },
  methods: {
    commentHandler() {
      const res = axios.post(
        `/api/support/addComment/${localStorage.getItem("id")}/${
          this.comments
        }/${localStorage.getItem("ticketId")}`
      );
      console.log(res);
      this.$toasted.success("Comment Added Successfully", {
        position: "top-center",
        duration: 2000,
      });
      this.comments = "";
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      this.fileName = file.name;
    },
    async onUpload() {
      const storageRef = ref(storage, `files/${this.fileName}`);
      if (this.fileName !== "") {
        await uploadBytes(storageRef, this.$refs.input.files[0]).then((res) => {
          console.log(res);
          this.$toasted.success("uploaded", {
            position: "top-center",
            duration: 2000,
          });
        });
        getDownloadURL(ref(storage, `files/${this.fileName}`)).then((res) => {
          this.url = res;
          console.log(this.url);
        });
      } else {
        this.$toasted.error("please select a file", {
          position: "top-center",
          duration: 2000,
        });
      }
    },
    async upload() {
      console.log("from firebase", this.url);
      const payload = {
        ticketId: localStorage.getItem("ticketId"),
        doc: this.url,
      };
      const res = await axios
        .post("/api/support/addDoc", payload)
        .then((res) => {
          this.$toasted.success("uploaded", {
            position: "top-center",
            duration: 2000,
          });
          console.log(res);
        });
      console.log(res);
      this.url = "";
      this.fileName = "";
      location.reload();
    },
  },
};
</script>
<style scoped>
.box1 {
  border-radius: 9px;
  width: 400px;
  height: 500px;
  margin-left: 500px;
  margin-top: 20px;
  padding: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
input {
  width: 300px;
}
</style>
