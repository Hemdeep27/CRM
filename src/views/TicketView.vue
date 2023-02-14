<template>
  <div class="main">
    <div class="left">
      <div class="inner" @click="OpenHandler">
        <h5>Open Tickets</h5>
      </div>
      <div class="inner" @click="AssignedHandler">
        <h5>Assgined Tickets</h5>
      </div>
      <div class="inner" @click="ProgressHandler">
        <h5>InProgress Tickets</h5>
      </div>
      <div class="inner" @click="ClosedHandler">
        <h5>Closed Tickets</h5>
      </div>
      <div class="inner" @click="CreateHandler">
        <h5>Create Tickets</h5>
      </div>
    </div>
    <div class="right">
      <!-- {{ getTicketList }} -->
      <h1 v-if="OpenTickets">Open Tickets</h1>
      <h1 v-if="Closed">Closed Tickets</h1>
      <h1 v-if="AssignedTickets">Assigned Tickets</h1>
      <h1 v-if="Inprogress">InProgress Tickets</h1>
      <h1 v-if="CreateTicket">Create Ticket</h1>
      <div class="inner-open" v-if="OpenTickets">
        <!-- {{ getTicketList }} -->
        <!-- {{ getSupportList }} -->
        <div
          class="forloop"
          v-for="(ticket, index) in getTicketList"
          :key="index"
        >
          <p><b> Ticket Id: </b>{{ getTicketList[index].ticketId }}</p>
          <p><b> User Email: </b>{{ getTicketList[index].userEmail }}</p>
          <p><b> Ticket Status: </b>{{ getTicketList[index].ticketStatus }}</p>
          <p><b> Ticket Info: </b>{{ getTicketList[index].ticketInfo }}</p>
          <div v-if="ticket.reason !== null">
            <b> Rejected: </b>
            <p v-for="(r, i) in getTicketList[index].reason" :key="i">
              {{ r }}
            </p>
          </div>
          <p>
            <b> Ticket Created: </b>
            {{ TimeChange(getTicketList[index].ticketCreatedTime) }}
          </p>
          <select
            v-model="supportEngineerId[index].value"
            @change="DropDownHandler"
            id="dropdown"
          >
            <option
              v-for="(support, index) in getSupportList"
              :key="index"
              :value="getSupportList[index].supportId"
            >
              <p>{{ support.supportName }} - {{ support.count }}</p>
              &nbsp;
              <span v-if="support.isActive" class="red-dot">&#9679;</span>
            </option>
          </select>
          <br />
          <button
            @click="
              AllotHandler(
                getTicketList[index].ticketId,
                supportEngineerId[index].value
              )
            "
            class="button-17"
          >
            Assign
          </button>
          <br />
        </div>
      </div>
      <div class="inner-open" v-if="Inprogress">
        <!-- <h1>inprogress</h1> -->
        <!-- {{ getTicketList }} -->
        <div
          v-for="progress in getTicketList"
          :key="progress.id"
          class="inprogress"
        >
          <p><b> TicketId: </b>{{ progress.ticketId }}</p>
          <p><b> UserEmail: </b>{{ progress.userEmail }}</p>
          <p><b> Ticket Status: </b>{{ progress.ticketStatus }}</p>
          <p><b> Ticket Assigned Id: </b>{{ progress.assignedToId }}</p>
          <p><b> Ticket Assigned Name: </b>{{ progress.assignedToName }}</p>
          <p>
            <b> Ticket Created: </b>
            {{ TimeChange(progress.ticketCreatedTime) }}
          </p>
        </div>
      </div>
      <div v-if="Closed" class="inner-open">
        <!-- <h1>closed</h1> -->
        <div v-for="closed in getTicketList" :key="closed.id" class="closed">
          <p><b> TicketId: </b>{{ closed.ticketId }}</p>
          <p><b> UserEmail: </b>{{ closed.userEmail }}</p>
          <p><b> Ticket Status: </b>{{ closed.ticketStatus }}</p>
          <p><b> Ticket Assigned Id: </b>{{ closed.assignedToId }}</p>
          <p><b> Ticket Assigned Name: </b>{{ closed.assignedToName }}</p>
          <div>
            <p v-for="(comm, index) in closed.comments" :key="index">
              <b>Ticket Comments: </b>{{ comm }}
            </p>
          </div>
          <div class="doc">
            <p v-for="(doc, index) in closed.doc" :key="index">
              <b> Ticket Docs: </b
              ><a :href="closed.doc[index]">{{ closed.doc[index] }} </a>
            </p>
          </div>
          <p>
            <b> Ticket Created: </b>
            {{ TimeChange(closed.ticketCreatedTime) }}
          </p>
        </div>
        <!-- {{ getTicketList }} -->
      </div>
      <div id="create" v-if="CreateTicket">
        <!-- <h1>CreateTicket</h1> -->
        <label>Ticket Info</label><br />
        <input type="text" v-model="ticketInfo" /><br />
        <label>User Email</label><br />
        <input type="text" v-model="UserEmail" /><br /><br />
        <button @click="SubmitHandler" class="button-19">Create Ticket</button>
      </div>
      <div class="inner-open" v-if="AssignedTickets">
        <!-- <h1>Assigned Tickets</h1> -->
        <!-- {{ getTicketList }} -->
        <div
          v-for="(assigned, index) in getTicketList"
          :key="index"
          class="assigned"
        >
          <p><b> Ticket Id: </b> {{ getTicketList[index].ticketId }}</p>
          <p><b> User Email: </b> {{ getTicketList[index].userEmail }}</p>
          <p><b> Ticket Status: </b> {{ getTicketList[index].ticketStatus }}</p>
          <p>
            <b> Ticket Assigned Id: </b> {{ getTicketList[index].assignedToId }}
          </p>
          <p>
            <b>Ticket Assigned Name: </b>
            {{ getTicketList[index].assignedToName }}
          </p>
          <p><b> Ticket Info: </b>{{ getTicketList[index].ticketInfo }}</p>
          <p>
            <b> Ticket Created: </b>
            {{ TimeChange(getTicketList[index].ticketCreatedTime) }}
          </p>
          <button v-if="boolValue" @click="boolHandler">Reject</button>
          <div v-if="boolValue2">
            <input type="text" v-model="reason" />
            <button @click="rejectHandler(getTicketList[index].ticketId)">
              Reject
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
export default {
  data() {
    return {
      OpenTickets: true,
      Inprogress: false,
      Closed: false,
      CreateTicket: false,
      AssignedTickets: false,
      ticketInfo: "",
      UserEmail: "",
      supportEngineerList: "",
      intial: "please Select",
      supportEngineerId: [],
      ticketIdNew: "",
      msg: true,
      reason: "",
      boolValue: true,
      boolValue2: false,
    };
  },
  computed: {
    ...mapGetters([
      "getTickets",
      "getTicketList",
      "getSupportList",
      "getTicketAssign",
    ]),
    handler(active) {
      if (active === true) {
        return true;
      }
      return false;
    },
  },
  created() {
    this.$store.dispatch("TICKET_LIST", {
      status: "open",
      success: (leads) => {
        for (let i = 0; i < leads.length; i++) {
          this.supportEngineerId.push({ value: "" });
        }
      },
    });
    this.$store.dispatch("SUPPORT_LIST");
  },
  methods: {
    ...mapActions(["TICKETS", "TICKET_LIST", "SUPPORT_LIST", "TICKET_ASSIGN"]),
    rejectHandler(tid) {
      axios.post(`/api/support/rejectTicketByTicketId/${tid}/${this.reason}`);
    },
    boolHandler() {
      this.boolValue = !this.boolValue;
      this.boolValue2 = !this.boolValue2;
    },
    TimeChange(time) {
      console.log(new Date(time).toLocaleString());
      return new Date(time).toLocaleString();
    },
    DropDownHandler(value) {
      value = this.supportEngineerId;
      console.log("support engineer List", value);
    },
    AllotHandler(tickId, supId) {
      console.log("ticket Id", this.ticketIdNew);
      console.log(this.supportEngineerId);
      this.$store.dispatch("TICKET_ASSIGN", {
        tId: tickId,
        sId: supId,
      });
      location.reload();
    },
    SubmitHandler() {
      const payload = {
        ticketInfo: this.ticketInfo,
        userEmail: this.UserEmail,
        userId: "",
      };
      axios.post("/api/admin/createTicket", payload);
      this.$toasted.success("Ticket created", {
        position: "top-center",
        duration: 2000,
      });
      this.ticketInfo = "";
      this.UserEmail = "";
    },
    ProgressHandler() {
      (this.OpenTickets = false),
        (this.Inprogress = true),
        (this.Closed = false);
      this.CreateTicket = false;
      this.AssignedTickets = false;
      this.$store.dispatch("TICKET_LIST", {
        status: "inProgress",
        success: (leads) => {
          for (let i = 0; i < leads.length; i++) {
            this.supportEngineerId.push({ value: "" });
          }
        },
      });
    },
    AssignedHandler() {
      (this.OpenTickets = false),
        (this.Inprogress = false),
        (this.Closed = false);
      this.CreateTicket = false;
      this.AssignedTickets = true;
      this.$store.dispatch("TICKET_LIST", {
        status: "assigned",
        success: (leads) => {
          for (let i = 0; i < leads.length; i++) {
            this.supportEngineerId.push({ value: "" });
          }
        },
      });
      // location.reload();
    },
    ClosedHandler() {
      this.OpenTickets = false;
      this.Inprogress = false;
      this.Closed = true;
      this.CreateTicket = false;
      this.AssignedTickets = false;
      this.$store.dispatch("TICKET_LIST", {
        status: "closed",
        success: (leads) => {
          for (let i = 0; i < leads.length; i++) {
            this.supportEngineerId.push({ value: "" });
          }
        },
      });
    },
    OpenHandler() {
      (this.OpenTickets = true),
        (this.Inprogress = false),
        (this.Closed = false);
      this.CreateTicket = false;
      this.AssignedTickets = false;
      console.log(this.supportEngineerId);
      this.$store.dispatch("TICKET_LIST", {
        status: "open",
        success: (leads) => {
          for (let i = 0; i < leads.length; i++) {
            this.supportEngineerId.push({ value: "" });
          }
        },
      });
      this.$store.dispatch("SUPPORT_LIST");
      // location.reload();
    },
    CreateHandler() {
      (this.OpenTickets = false),
        (this.Inprogress = false),
        (this.Closed = false);
      this.CreateTicket = true;
      this.AssignedTickets = false;
      // // this.$store.dispatch("TICKETS_LIST", { details: payload });
    },
  },
};
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: row;
  margin: 0;
  background-color: white;
}
.inner-open {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  flex-wrap: wrap;
  background-color: white;
  /* height: 100vh; */
}
.assigned {
  /* border: 2px solid black; */
  padding: 15px;
  width: 500px;
  margin: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  border-radius: 9px;
  text-align: left;
  /* padding-left: 15px; */
}
.inprogress {
  /* border: 2px solid black; */
  width: 500px;
  margin: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  border-radius: 9px;
  padding: 15px;
  text-align: left;
}
.closed {
  /* border: 2px solid black; */
  width: 500px;
  margin: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  border-radius: 9px;
  padding: 15px;
  text-align: left;
}
.left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 25%;
  /* background-color: beige; */
  height: 100vh;
  margin-left: 0;
  /* border: 1px solid black; */
  position: fixed;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  /* border-radius: px; */
}
.inner {
  /* border: 1px solid black; */
  border-radius: 9px;
  margin: 20px;
  align-items: flex-end;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  cursor: pointer;
  padding-top: 10px;
}
/* CSS */
.button-17 {
  align-items: center;
  appearance: none;
  background-color: #fff;
  border-radius: 24px;
  border-style: none;
  box-shadow: rgba(0, 0, 0, 0.2) 0 3px 5px -1px,
    rgba(0, 0, 0, 0.14) 0 6px 10px 0, rgba(0, 0, 0, 0.12) 0 1px 18px 0;
  box-sizing: border-box;
  color: #3c4043;
  cursor: pointer;
  display: inline-flex;
  fill: currentcolor;
  font-family: "Google Sans", Roboto, Arial, sans-serif;
  font-size: 14px;
  font-weight: 500;
  height: 48px;
  justify-content: center;
  letter-spacing: 0.25px;
  line-height: normal;
  max-width: 100%;
  overflow: visible;
  padding: 2px 24px;
  position: relative;
  text-align: center;
  text-transform: none;
  transition: box-shadow 280ms cubic-bezier(0.4, 0, 0.2, 1),
    opacity 15ms linear 30ms, transform 270ms cubic-bezier(0, 0, 0.2, 1) 0ms;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  width: auto;
  will-change: transform, opacity;
  z-index: 0;
  float: right;
}

.button-17:hover {
  background: #f6f9fe;
  color: #174ea6;
}

.button-17:active {
  box-shadow: 0 4px 4px 0 rgb(60 64 67 / 30%),
    0 8px 12px 6px rgb(60 64 67 / 15%);
  outline: none;
}

.button-17:focus {
  outline: none;
  border: 2px solid #4285f4;
}

.button-17:not(:disabled) {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}

.button-17:not(:disabled):hover {
  box-shadow: rgba(60, 64, 67, 0.3) 0 2px 3px 0,
    rgba(60, 64, 67, 0.15) 0 6px 10px 4px;
}

.button-17:not(:disabled):focus {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}

.button-17:not(:disabled):active {
  box-shadow: rgba(60, 64, 67, 0.3) 0 4px 4px 0,
    rgba(60, 64, 67, 0.15) 0 8px 12px 6px;
}

.button-17:disabled {
  box-shadow: rgba(60, 64, 67, 0.3) 0 1px 3px 0,
    rgba(60, 64, 67, 0.15) 0 4px 8px 3px;
}
.right {
  width: 70%;
  /* background-color: aqua; */
  margin-left: 25%;
  height: 100%;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-between;
  /* border: 1px solid black; */
}
#dropdown {
  width: 300px;
}
.forloop {
  /* border: 2px solid black; */
  width: 500px;
  margin: 10px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  border-radius: 9px;
  padding: 15px;
  text-align: left;
}
.red-dot {
  height: 10px;
  width: 10px;
  background-color: green;
  border-radius: 50%;
  display: inline-block;
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
  height: 40px;
}

#create {
  border: 2px solid black;
  width: 400px;
  float: center;
  margin-left: 30%;
  height: 300px;
  margin-top: 10%;
}

.doc {
  word-wrap: break-word;
}
</style>
