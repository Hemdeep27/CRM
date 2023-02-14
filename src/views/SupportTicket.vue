<template>
  <div>
    <!-- <nav-bar-1></nav-bar-1> -->
    <div class="main">
      <div class="left">
        <div class="inner" @click="AssignedHandler">
          <h5>Assgined Tickets</h5>
        </div>
        <div class="inner" @click="ProgressHandler">
          <h5>InProgress Tickets</h5>
        </div>
        <div class="inner" @click="ClosedHandler">
          <h5>Closed Tickets</h5>
        </div>
      </div>
      <div class="right">
        <div class="inner-right" v-if="createdmsg">
          <h2 class="one">Assigned Tickets</h2>
          <!-- {{ getTicketList }} -->
          <div
            v-for="(ticket, index) in AssignedList"
            :key="index"
            class="inner-inner-right"
          >
            <p><b>TicketId: </b>{{ ticket.ticketId }}</p>
            <p><b>TicketInfo: </b>{{ ticket.ticketInfo }}</p>
            <p><b>UserEmail: </b>{{ ticket.userEmail }}</p>
            <p><b>UserId: </b>{{ ticket.userId }}</p>
            <p>{{ TimeChange(AssignedList[index].ticketCreatedTime) }}</p>
            <button
              type="button"
              class="btn btn-outline-primary"
              @click="changeStatus1(ticket.ticketId)"
            >
              Accept
            </button>
            &nbsp;
            <button
              type="button"
              class="btn btn-outline-danger"
              v-if="boolValue2"
              @click="boolHandler"
            >
              Reject
            </button>
            <div v-if="boolValue">
              <input
                type="text"
                v-model="reason"
                v-if="boolValue"
                placeholder="Enter Reason"
              />&nbsp;
              <button
                type="button"
                class="btn btn-outline-danger"
                style="float: center"
                @click="rejectHandler(ticket.ticketId)"
              >
                Reject
              </button>
            </div>
          </div>
        </div>
        <div class="inner-right" v-if="Inprogress">
          <div class="one"><h2>inprogress</h2></div>
          <!-- {{ getTicketList }} -->
          <div
            v-for="(ticket, index) in inProgressList"
            :key="index"
            class="inner-inner-right"
          >
            <p><b>AssignedToName: </b>{{ ticket.assignedToName }}</p>
            <p><b>TicketId: </b>{{ ticket.ticketId }}</p>
            <p><b>TicketInfo: </b>{{ ticket.ticketInfo }}</p>
            <!-- <p v-for="(comment, index) in ticket.comments" :key="index"> -->
            <div>
              <div v-for="(comment, index) in ticket.comments" :key="index">
                <b>Comments: </b>{{ index }} -
                <div v-for="(c, i) in ticket.comments[index]" :key="i">
                  {{ c }}
                </div>
              </div>
            </div>
            <!-- </p> -->
            <!-- <p v-for="(doc, index) in ticket.doc" :key="index"> -->
            <div class="doc">
              <p v-for="(doc, index) in ticket.doc" :key="index">
                <b>Doc: </b>
                <img
                  :src="ticket.doc[index]"
                  v-if="
                    ticket.doc[index].includes('jpg') ||
                    ticket.doc[index].includes('png') ||
                    ticket.doc[index].includes('jpeg')
                  "
                />
                <a :href="ticket.doc[index]" v-else target="_blank"
                  >Download Doc</a
                >
              </p>
            </div>
            <!-- </p> -->
            <p><b>TicketStatus: </b>{{ ticket.ticketStatus }}</p>
            <p><b>UserEmail: </b>{{ ticket.userEmail }}</p>
            <p><b>UserId: </b>{{ ticket.userId }}</p>
            <p>{{ TimeChange(ticket.ticketCreatedTime) }}</p>

            <button
              @click="editTicket(ticket.ticketId)"
              type="button"
              class="btn btn-outline-primary"
            >
              Edit
            </button>
            &nbsp;
            <button
              type="button"
              class="btn btn-outline-success"
              @click="changeStatus(ticket.ticketId)"
            >
              Close
            </button>
            &nbsp;
            <button
              type="button"
              class="btn btn-outline-danger"
              v-if="boolValue2"
              @click="boolHandler"
            >
              Reject
            </button>
            <div v-if="boolValue">
              <input
                type="text"
                v-model="reason"
                v-if="boolValue"
                placeholder="Enter Reason"
              />&nbsp;
              <button
                type="button"
                class="btn btn-outline-danger"
                style="float: center"
                @click="rejectHandler(ticket.ticketId)"
              >
                Reject
              </button>
            </div>
          </div>
        </div>
        <div class="inner-right" v-if="Closed">
          <h2 class="one">closed</h2>
          <!-- {{ getTicketList }} -->
          <div
            v-for="(ticket, index) in closedList"
            :key="index"
            class="inner-inner-right"
          >
            <p><b>AssignedToName: </b>{{ ticket.assignedToName }}</p>
            <p><b>TicketId: </b>{{ ticket.ticketId }}</p>
            <p><b>TicketInfo: </b>{{ ticket.ticketInfo }}</p>
            <p><b>TicketStatus: </b>{{ ticket.ticketStatus }}</p>
            <p><b>UserEmail: </b>{{ ticket.userEmail }}</p>
            <p><b>UserId: </b>{{ ticket.userId }}</p>
            <div>
              <div v-for="(comment, index) in ticket.comments" :key="index">
                <b>Comments: </b>{{ index }} -
                <div v-for="(c, i) in ticket.comments[index]" :key="i">
                  {{ c }}
                </div>
              </div>
            </div>
            <div class="doc">
              <p v-for="(doc, index) in ticket.doc" :key="index">
                <b>Doc: </b>
                <img
                  :src="ticket.doc[index]"
                  v-if="
                    ticket.doc[index].includes('jpg') ||
                    ticket.doc[index].includes('png') ||
                    ticket.doc[index].includes('jpeg')
                  "
                />
                <a :href="ticket.doc[index]" target="_blank" v-else
                  >Download Doc</a
                >
              </p>
            </div>
            <p>{{ TimeChange(ticket.ticketCreatedTime) }}</p>

            <!-- <button>Edit</button> -->
            <!-- <button @click="changeStatus(ticket.ticketId)">Close</button> -->
          </div>
        </div>
        <div class="inner-right" v-if="AssignedTickets">
          <h2 class="one">Assigned Tickets</h2>
          <!-- {{ getTicketList }} -->
          <div
            v-for="(ticket, index) in AssignedList"
            :key="index"
            class="inner-inner-right"
          >
            <p><b>TicketId: </b>{{ ticket.ticketId }}</p>
            <p><b>TicketInfo: </b>{{ ticket.ticketInfo }}</p>
            <p><b>UserEmail: </b>{{ ticket.userEmail }}</p>
            <p><b>UserId: </b>{{ ticket.userId }}</p>
            <p>{{ TimeChange(AssignedList[index].ticketCreatedTime) }}</p>
            <button
              type="button"
              class="btn btn-outline-primary"
              @click="changeStatus1(ticket.ticketId)"
            >
              Accept
            </button>
            &nbsp;
            <button
              type="button"
              class="btn btn-outline-danger"
              v-if="boolValue2"
              @click="boolHandler"
            >
              Reject
            </button>
            <div v-if="boolValue">
              <input
                type="text"
                v-model="reason"
                v-if="boolValue"
                placeholder="Enter Reason"
              />&nbsp;
              <button
                type="button"
                class="btn btn-outline-danger"
                style="float: center"
                @click="rejectHandler(ticket.ticketId)"
              >
                Reject
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import NavBar1 from "@/components/NavBar1.vue";
import axios from "axios";
export default {
  components: {
    // NavBar1,
  },
  data() {
    return {
      createdmsg: true,
      Inprogress: false,
      Closed: false,
      AssignedTickets: false,
      Open: true,
      OpenList: [],
      inProgressList: [],
      AssignedList: [],
      closedList: [],
      StatusList: [],
      reason: "",
      boolValue: false,
      boolValue2: true,
    };
  },
  async created() {
    await axios
      .get(
        "/api/support/getAssignedTicketsByStatus/assigned/" +
          localStorage.getItem("id")
      )
      .then((res) => {
        this.AssignedList = res.data;
      });
    console.log(this.AssignedList);
  },
  methods: {
    boolHandler() {
      this.boolValue = !this.boolValue;
      this.boolValue2 = !this.boolValue2;
    },
    async rejectHandler(tid) {
      // debugger;
      await axios.post(
        `/api/support/rejectTicketByTicketId/${tid}/${this.reason}`
      );
      location.reload();
    },
    async editTicket(ticketId) {
      localStorage.setItem("ticketId", ticketId);
      this.$router.push("/editticket");
    },

    TimeChange(time) {
      console.log(new Date(time).toLocaleString());
      return new Date(time).toLocaleString();
    },

    async changeStatus(ticketId) {
      await axios.post(
        "/api/support/editStatusOfTicket/" + ticketId + "/" + "closed"
      );
      location.reload();
    },
    async changeStatus1(ticketId) {
      console.log(ticketId);
      await axios.post(
        "/api/support/editStatusOfTicket/" + ticketId + "/" + "inProgress"
      );
      location.reload();
    },

    async ProgressHandler() {
      (this.createdmsg = false),
        (this.AssignedTickets = false),
        (this.Inprogress = true),
        (this.Closed = false);
      await axios
        .get(
          "/api/support/getAssignedTicketsByStatus/inProgress/" +
            localStorage.getItem("id")
        )
        .then((res) => {
          this.inProgressList = res.data;
        });
      console.log(this.inProgressList);
    },
    async AssignedHandler() {
      (this.createdmsg = false),
        (this.AssignedTickets = true),
        (this.Inprogress = false),
        (this.Closed = false);
      await axios
        .get(
          "/api/support/getAssignedTicketsByStatus/assigned/" +
            localStorage.getItem("id")
        )
        .then((res) => {
          this.AssignedList = res.data;
        });
      console.log(this.AssignedList);
    },
    async ClosedHandler() {
      (this.createdmsg = false),
        (this.AssignedTickets = false),
        (this.Inprogress = false),
        (this.Closed = true);
      await axios
        .get(
          "/api/support/getAssignedTicketsByStatus/closed/" +
            localStorage.getItem("id")
        )
        .then((res) => {
          this.closedList = res.data;
        });
      console.log(this.closedList);
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
input {
  width: 300px;
}
.left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 25vw;
  /* background-color: beige; */
  height: 100vh;
  margin-left: 0;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  /* border: 1px solid black; */
  position: fixed;
}
.inner {
  /* border: 1px solid black; */
  margin: 20px;
  border-radius: 6px;
  padding-bottom: 10px;
  padding-top: 10px;
  align-items: flex-end;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  cursor: pointer;
}

.inner-inner-right {
  width: 500px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.181);
  margin: 20px;
  border-radius: 9px;
  text-align: left;
  padding: 10px;
}
.right {
  width: 70%;
  /* background-color: aqua; */
  height: 100vh;
  margin-left: 40vw;
  /* display: flex; */
}

.one {
  margin-right: 25vw;
  /* text-align: center; */
  /* margin-left: 0px; */
}

.forloop {
  border: 2px solid black;
  width: 50%;
  margin: 10px;
}
.doc {
  word-wrap: break-word;
  display: inline-block;
}

img {
  width: 100px;
  height: 100px;
}
</style>
