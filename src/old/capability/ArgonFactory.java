package tk.dczippl.lightestlamp.util.capability;

import jdk.internal.dynalink.linker.LinkerServices;

import java.util.concurrent.Callable;

public class ArgonFactory implements Callable<IArgon>
{
    @Override
    public IArgon call() throws Exception {
        return new Argon();
    }
}