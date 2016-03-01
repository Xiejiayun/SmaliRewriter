.class Lcom/example/messagesmali/SinkSetAPI$1;
.super Landroid/content/BroadcastReceiver;
.source "SinkSetAPI.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/messagesmali/SinkSetAPI;->contextSinkMethod()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/example/messagesmali/SinkSetAPI;


# direct methods
.method constructor <init>(Lcom/example/messagesmali/SinkSetAPI;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/example/messagesmali/SinkSetAPI$1;->this$0:Lcom/example/messagesmali/SinkSetAPI;

    .line 128
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 0
    .parameter "arg0"
    .parameter "arg1"

    .prologue
    .line 134
    return-void
.end method
