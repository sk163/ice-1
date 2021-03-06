// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

module Test
{

module Common
{

interface Server
{
    void waitTestSuccess();
    void waitForServer();
    void terminate();
};

sequence<string> StringSeq;

interface Controller
{
    Server* runServer(string lang, string name, string protocol, string host, bool winrt, 
                      StringSeq options);
};

};

};
