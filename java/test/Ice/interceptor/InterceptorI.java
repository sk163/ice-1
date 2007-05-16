// **********************************************************************
//
// Copyright (c) 2003-2007 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

class InterceptorI extends Ice.DispatchInterceptor
{
    InterceptorI(Ice.Object servant)
    {
        _servant = servant;
    }

    protected static void
    test(boolean b)
    {
        if(!b)
        {
            throw new RuntimeException();
        }
    }

    
    public IceInternal.DispatchStatus
    dispatch(Ice.Request request)
    {
        Ice.Current current = request.getCurrent();
        _lastOperation = current.operation;

        if(_lastOperation.equals("addWithRetry"))
        {
            for(int i = 0; i < 10; ++i)
            {
                try
                {
                    _servant.ice_dispatch(request, null);
                    test(false);
                }
                catch(Test.RetryException re)
                {
                    //
                    // Expected, retry
                    //
                }
            }
            
            current.ctx.put("retry", "no");
        }
      
        _lastStatus = _servant.ice_dispatch(request, null);
        return _lastStatus;
    }

    IceInternal.DispatchStatus
    getLastStatus()
    {
        return _lastStatus;
    }

    String
    getLastOperation()
    {
        return _lastOperation;
    }

    void
    clear()
    {
        _lastOperation = null;
        _lastStatus = null;
    }

    protected final Ice.Object _servant;
    protected String _lastOperation;
    protected IceInternal.DispatchStatus _lastStatus;
}
