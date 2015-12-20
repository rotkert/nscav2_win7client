using System;
using System.Net;
using System.Net.Sockets;

namespace PerfCountersCollector
{
    class Sender
    {
        private int port = 0;

        public void setPort(int port)
        {
            this.port = port;
        }

        public bool sendInfo(String message)
        {
            bool isSent = true;
            IPEndPoint serverAddress = new IPEndPoint(IPAddress.Parse("127.0.0.1"), port);
            Socket clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            
            try
            {
                clientSocket.Connect(serverAddress);
                byte[] toSendBytes = System.Text.Encoding.UTF8.GetBytes(message);
                clientSocket.Send(toSendBytes);
            }
            catch (SocketException)
            {
                isSent = false;
            }
            clientSocket.Close();

            return isSent;
        }
    }
}